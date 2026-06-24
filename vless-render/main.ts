import { encodeBase64 } from "https://deno.land/std@0.224.0/encoding/base64.ts";

const UUID = Deno.env.get("VLESS_UUID") || "cb2152c9-0545-4b5e-9874-f87c24be8bcb";
const PORT = parseInt(Deno.env.get("PORT") || "3000");

// VMess over WebSocket - simplified implementation
// Parse VMess header: version(1) + IV(16) + Key(16) + Opt(1) + ...
// For simplicity, we'll use a plain-text auth mode compatible with sing-box

// Simple VLESS implementation that works with sing-box
const UUID_BUF = new Uint8Array(16);
for (let i = 0; i < 16; i++) {
  UUID_BUF[i] = parseInt(UUID.replace(/-/g, "").substring(i * 2, i * 2 + 2), 16);
}

function parseHeader(buf: Uint8Array): { addr: string; port: number; headerLen: number } | null {
  if (buf.length < 18) return null;
  
  const version = buf[0];
  if (version !== 0) throw new Error("Unsupported VLESS version");

  // Verify UUID (bytes 1-16)
  const uuidBuf = buf.subarray(1, 17);
  for (let i = 0; i < 16; i++) {
    if (uuidBuf[i] !== UUID_BUF[i]) throw new Error("UUID mismatch");
  }

  const addonLen = buf[17];
  let offset = 18 + addonLen;
  if (buf.length < offset + 4) return null;

  offset += 1; // command (skip)
  const port = (buf[offset] << 8) | buf[offset + 1];
  offset += 2;
  const addrType = buf[offset];
  offset += 1;

  let addr: string;
  switch (addrType) {
    case 0x01:
      if (buf.length < offset + 4) return null;
      addr = `${buf[offset]}.${buf[offset+1]}.${buf[offset+2]}.${buf[offset+3]}`;
      offset += 4;
      break;
    case 0x02:
      if (buf.length < offset + 1) return null;
      const dLen = buf[offset]; offset += 1;
      if (buf.length < offset + dLen) return null;
      addr = new TextDecoder().decode(buf.subarray(offset, offset + dLen));
      offset += dLen;
      break;
    case 0x03:
      if (buf.length < offset + 16) return null;
      const p: string[] = [];
      for (let i = 0; i < 16; i += 2) p.push(((buf[offset+i] << 8) | buf[offset+i+1]).toString(16));
      addr = p.join(":");
      offset += 16;
      break;
    default:
      throw new Error(`Unknown addr type: ${addrType}`);
  }
  return { addr, port, headerLen: offset };
}

Deno.serve({ port: PORT }, async (req: Request): Promise<Response> => {
  const url = new URL(req.url);
  
  if (url.pathname === "/health" || url.pathname === "/") {
    return new Response("VLESS-WS proxy is running", { status: 200 });
  }

  if (req.headers.get("upgrade")?.toLowerCase() === "websocket") {
    const { socket, response } = Deno.upgradeWebSocket(req);
    let stage = 0;
    let headerBuf = new Uint8Array(0);
    let targetConn: Deno.TcpConn | null = null;
    let version = 0;

    socket.onmessage = async (e) => {
      const data = e.data instanceof ArrayBuffer ? new Uint8Array(e.data) : 
                   new Uint8Array(await (e.data as Blob).arrayBuffer());

      if (stage === 0) {
        const merged = new Uint8Array(headerBuf.length + data.length);
        merged.set(headerBuf);
        merged.set(data, headerBuf.length);
        headerBuf = merged;

        try {
          const result = parseHeader(headerBuf);
          if (!result) return;

          version = headerBuf[0];
          const { addr, port, headerLen } = result;
          const payload = headerBuf.subarray(headerLen);

          console.log(`VLESS -> ${addr}:${port} (${payload.length} bytes init payload)`);

          try {
            targetConn = await Deno.connect({ hostname: addr, port });
            stage = 1;

            // VLESS response: version(1) + addonLen(1,=0) + cmd(1,=0)
            socket.send(new Uint8Array([version, 0, 0]));

            if (payload.length > 0) {
              await targetConn.write(payload);
            }

            // Pipe target -> websocket
            (async () => {
              try {
                const buf = new Uint8Array(32768);
                while (true) {
                  const n = await targetConn!.read(buf);
                  if (n === null) break;
                  if (socket.readyState === WebSocket.OPEN) {
                    socket.send(buf.subarray(0, n));
                  }
                }
              } catch (_e) { /* closed */ }
              socket.close();
            })();

          } catch (err) {
            console.error(`TCP connect failed: ${addr}:${port} - ${err}`);
            socket.close();
          }
        } catch (err) {
          console.error("Header parse error:", err);
          socket.close();
        }
      } else if (stage === 1 && targetConn) {
        try { await targetConn.write(data); } catch { socket.close(); }
      }
    };

    socket.onclose = () => { if (targetConn) try { targetConn.close(); } catch {} };
    socket.onerror = () => { if (targetConn) try { targetConn.close(); } catch {} };

    return response;
  }

  return new Response("Not Found", { status: 404 });
});
