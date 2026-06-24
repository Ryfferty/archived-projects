package com.xspaceagi.system.spec.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class IPUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isInternalAddress(String url) throws URISyntaxException, UnknownHostException {
        URI uri = new URI(url);
        String host = uri.getHost();

        // 检查是否为本地回环地址或localhost
        if (host == null || host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1")) {
            return true;
        }

        // 解析IP地址
        InetAddress address = InetAddress.getByName(host);
        byte[] ipBytes = address.getAddress();

        // 检查是否为内网IP地址
        if (isPrivateIP(ipBytes)) {
            return true;
        }

        // 检查是否为内网域名（如公司内部域名）
        if (host.endsWith(".local") || host.endsWith(".internal")) {
            return true;
        }

        return false;
    }

    private static boolean isPrivateIP(byte[] ipBytes) {
        // 10.0.0.0 - 10.255.255.255
        if (ipBytes[0] == 10) {
            return true;
        }
        // 172.16.0.0 - 172.31.255.255
        if (ipBytes[0] == (byte) 172 && ipBytes[1] >= 16 && ipBytes[1] <= 31) {
            return true;
        }
        // 192.168.0.0 - 192.168.255.255
        if (ipBytes[0] == (byte) 192 && ipBytes[1] == (byte) 168) {
            return true;
        }
        return false;
    }
}
