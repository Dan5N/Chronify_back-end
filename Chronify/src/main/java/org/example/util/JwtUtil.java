package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * 提供JWT Token的生成、解析、验证等功能
 * 用于用户认证和授权
 *
 * @author Chronify
 * @since 1.0.0
 */
public class JwtUtil {

    /**
     * JWT密钥
     * 生产环境应从配置文件中读取，不要硬编码
     */
    private static final String SECRET_KEY = "chronify-secret-key-2024";

    /**
     * Token过期时间：24小时
     * 单位：毫秒
     */
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24小时

    /**
     * 生成JWT Token
     *
     * @param username 用户名
     * @param userId 用户ID
     * @return JWT Token字符串
     */
    public static String generateToken(String username, Long userId) {
        // 创建自定义声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userId", userId);

        // 构建JWT Token
        return Jwts.builder()
                .setClaims(claims)              // 设置自定义声明
                .setSubject(username)            // 设置主题
                .setIssuedAt(new Date())         // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 设置签名算法和密钥
                .compact();
    }

    /**
     * 解析JWT Token
     *
     * @param token JWT Token字符串
     * @return Claims对象，包含Token中的所有声明
     * @throws Exception Token无效或解析失败时抛出异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从Token中获取用户名
     *
     * @param token JWT Token字符串
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        return parseToken(token).get("username", String.class);
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token JWT Token字符串
     * @return 用户ID
     */
    public static Long getUserIdFromToken(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * 检查Token是否过期
     *
     * @param token JWT Token字符串
     * @return true-已过期，false-未过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            // 解析失败视为过期
            return true;
        }
    }

    /**
     * 验证Token是否有效
     *
     * @param token JWT Token字符串
     * @return true-有效，false-无效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);  // 尝试解析，无效会抛出异常
            return !isTokenExpired(token);  // 检查是否过期
        } catch (Exception e) {
            return false;
        }
    }
}