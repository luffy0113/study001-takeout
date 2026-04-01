package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /*
    *生成 JWT 令牌
    * @param secretKey 密钥（相当于手环的防伪码）
    * @param ttlMillis 有效期，毫秒
    * @param claims    要装进去的数据
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(exp)
                .compact();
    }
    /*
     *解析 JWT 令牌
     * @param secretKey 密钥（和生成的相同）
     * @token           要解析的令牌
     */
    public static Claims parseJWT(String secretKey, String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
