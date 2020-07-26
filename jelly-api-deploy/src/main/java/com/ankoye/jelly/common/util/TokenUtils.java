package com.ankoye.jelly.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt token工具类：
 * 提供校验token 、生成token、根据token获取用户等方法
 * @version   1.0.0
 */
public class TokenUtils implements Serializable {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final Long EXPIRATION = 60 * 60 * 24 * 3L;

    /**
     * 生成token
     */
    public static String generateToken(String userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.DEFLATE)            // 压缩，可选GZIP
                .signWith(SignatureAlgorithm.HS256, generalKey())   // 加密
                .setExpiration(generateExpirationDate(EXPIRATION)).compact();
    }

    /**
     * 验证token
     * @return true：表示验证通过
     */
    public static Boolean validateToken(String token) {
        final Claims claims = parse(token);
        if(claims == null) return false;
        // 这里就简单验证下
        final String userId = claims.get("userId").toString();
        final String username = claims.getSubject();
        if (username != null && userId != null) {
            return true;
        }
        return false;
    }

    // 从token中获取Claims
    public static Claims parse(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // expiration 单位秒
    private static Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    // 生成key
    private static SecretKey generalKey(){
        String stringKey = "7786df7fc3a34e26a61c034d5ec8245d";
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}