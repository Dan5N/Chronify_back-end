package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Utility Class
 *
 * Provides JWT Token generation, parsing, validation and other functions
 * Used for user authentication and authorization
 *
 * @author Chronify
 * @since 1.0.0
 */
public class JwtUtil {

    /**
     * JWT secret key
     * Production environment should read from configuration file, do not hardcode
     */
    private static final String SECRET_KEY = "chronify-secret-key-2024";

    /**
     * Token expiration time: 24 hours
     * Unit: milliseconds
     */
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    /**
     * Generate JWT Token
     *
     * @param username Username
     * @param userId User ID
     * @return JWT Token string
     */
    public static String generateToken(String username, Long userId) {
        // Create custom claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userId", userId);

        // Build JWT Token
        return Jwts.builder()
                .setClaims(claims)              // Set custom claims
                .setSubject(username)            // Set subject
                .setIssuedAt(new Date())         // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Set signature algorithm and key
                .compact();
    }

    /**
     * Parse JWT Token
     *
     * @param token JWT Token string
     * @return Claims object, containing all claims in the Token
     * @throws Exception Throws exception when Token is invalid or parsing fails
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Get username from Token
     *
     * @param token JWT Token string
     * @return Username
     */
    public static String getUsernameFromToken(String token) {
        return parseToken(token).get("username", String.class);
    }

    /**
     * Get user ID from Token
     *
     * @param token JWT Token string
     * @return User ID
     */
    public static Long getUserIdFromToken(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * Check if Token is expired
     *
     * @param token JWT Token string
     * @return true-expired, false-not expired
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            // Parsing failure is considered expired
            return true;
        }
    }

    /**
     * Validate if Token is valid
     *
     * @param token JWT Token string
     * @return true-valid, false-invalid
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);  // Try to parse, invalid will throw exception
            return !isTokenExpired(token);  // Check if expired
        } catch (Exception e) {
            return false;
        }
    }
}