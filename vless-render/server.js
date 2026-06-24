const http = require('http');
const { WebSocketServer } = require('ws');
const net = require('net');
const crypto = require('crypto');

const UUID = process.env.VLESS_UUID || 'cb2152c9-0545-4b5e-9874-f87c24be8bcb';
const PORT = process.env.PORT || 3000;
const UUID_BUF = Buffer.from(UUID.replace(/-/g, ''), 'hex');

// Health check server
const server = http.createServer((req, res) => {
  if (req.url === '/health' || req.url === '/') {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end('VLESS-WS proxy is running');
  } else {
    res.writeHead(404);
    res.end('Not Found');
  }
});

const wss = new WebSocketServer({ server, path: '/' });

wss.on('connection', (ws, req) => {
  let stage = 0; // 0=handshake, 1=data
  let targetSocket = null;
  let headerBuf = Buffer.alloc(0);

  ws.on('message', (data) => {
    const buf = Buffer.isBuffer(data) ? data : Buffer.from(data);

    if (stage === 0) {
      headerBuf = Buffer.concat([headerBuf, buf]);
      try {
        const result = parseVlessHeader(headerBuf);
        if (!result) return; // need more data

        const { version, targetAddr, targetPort, headerLen } = result;

        // Verify UUID
        const uuidBuf = headerBuf.subarray(1, 17);
        if (!uuidBuf.equals(UUID_BUF)) {
          console.error('UUID mismatch');
          ws.close();
          return;
        }

        // Extract initial payload
        const payloadOffset = headerLen;
        const initialPayload = headerBuf.subarray(payloadOffset);

        // Connect to target
        const addr = targetAddr;
        const port = targetPort;
        console.log(`Connecting to ${addr}:${port}`);

        targetSocket = net.createConnection({ host: addr, port: port }, () => {
          stage = 1;
          // Send VLESS response header
          const respHeader = Buffer.from([version, 0, 0]); // version, addonLen=0, cmd=0
          ws.send(respHeader);

          // Send initial payload
          if (initialPayload.length > 0) {
            targetSocket.write(initialPayload);
          }
        });

        targetSocket.on('data', (chunk) => {
          if (ws.readyState === 1) {
            ws.send(chunk);
          }
        });

        targetSocket.on('error', (err) => {
          console.error('Target error:', err.message);
          ws.close();
        });

        targetSocket.on('close', () => {
          ws.close();
        });

      } catch (err) {
        console.error('Header parse error:', err.message);
        ws.close();
      }
    } else if (stage === 1) {
      // Forward data to target
      if (targetSocket && !targetSocket.destroyed) {
        targetSocket.write(buf);
      }
    }
  });

  ws.on('close', () => {
    if (targetSocket) {
      targetSocket.destroy();
    }
  });

  ws.on('error', (err) => {
    console.error('WS error:', err.message);
    if (targetSocket) targetSocket.destroy();
  });
});

function parseVlessHeader(buf) {
  if (buf.length < 18) return null; // min: version(1) + uuid(16) + addonLen(1)

  const version = buf[0];
  if (version !== 0) throw new Error('Unsupported version');

  // UUID is bytes 1-16 (already verified before calling)
  const addonLen = buf[17];
  let offset = 18 + addonLen;

  if (buf.length < offset + 4) return null; // need more: cmd(1)+port(2)+addrType(1)

  // Skip addons
  offset += 1; // command (1=TCP, 2=UDP, we ignore and use TCP)

  const port = buf.readUInt16BE(offset);
  offset += 2;

  const addrType = buf[offset];
  offset += 1;

  let addr;
  switch (addrType) {
    case 0x01: // IPv4
      if (buf.length < offset + 4) return null;
      addr = buf.subarray(offset, offset + 4).join('.');
      offset += 4;
      break;
    case 0x02: // Domain
      if (buf.length < offset + 1) return null;
      const domainLen = buf[offset];
      offset += 1;
      if (buf.length < offset + domainLen) return null;
      addr = buf.subarray(offset, offset + domainLen).toString('utf8');
      offset += domainLen;
      break;
    case 0x03: // IPv6
      if (buf.length < offset + 16) return null;
      const parts = [];
      for (let i = 0; i < 16; i += 2) {
        parts.push(buf.readUInt16BE(offset + i).toString(16));
      }
      addr = parts.join(':');
      offset += 16;
      break;
    default:
      throw new Error('Unknown address type: ' + addrType);
  }

  return { version, targetAddr: addr, targetPort: port, headerLen: offset };
}

server.listen(PORT, () => {
  console.log(`VLESS-WS proxy listening on :${PORT}`);
  console.log(`UUID: ${UUID}`);
});
