package com.xspaceagi.system.spec.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * 创建jwt
     *
     * @param id
     * @param user
     * @param secretKey
     * @param expireSec
     * @param params
     * @return
     */
    public static String createJwt(String id, String user, String secretKey, int expireSec, Map<String, String> params) {
        JwtBuilder jwtBuilder = Jwts.builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                jwtBuilder.claim(entry.getKey(), entry.getValue());
            }
        }
        jwtBuilder.setId(id).setSubject(user).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expireSec * 1000L))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes());
        return jwtBuilder.compact();
    }

    /**
     * 解析jwt
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static Claims parseJwt(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }
}
