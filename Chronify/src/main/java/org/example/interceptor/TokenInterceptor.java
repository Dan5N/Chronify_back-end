package org.example.interceptor;

import org.example.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Token Authentication Interceptor
 *
 * Intercepts API requests that require authentication, validates JWT Token validity
 * Parses user information and stores it in request attributes for subsequent use
 *
 * @author Chronify
 * @since 1.0.0
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * Intercept and validate before request processing
     *
     * @param request  HTTP request object
     * @param response HTTP response object
     * @param handler  Handler object
     * @return true-continue processing, false-interrupt processing
     * @throws Exception Processing exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Handle OPTIONS requests (CORS preflight requests)
        // OPTIONS requests do not require Token validation
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // Get Token from request header
        String token = getTokenFromRequest(request);

        // Check if Token exists
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"Authentication token not provided\"}");
            return false;
        }

        // Validate Token validity
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"Token is invalid or expired\"}");
            return false;
        }

        // Parse user information from Token
        Long userId = JwtUtil.getUserIdFromToken(token);
        String username = JwtUtil.getUsernameFromToken(token);

        // Store user information in request attribute for easy access by subsequent Controllers
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);

        return true;
    }

    /**
     * Extract JWT Token from HTTP request
     *
     * @param request HTTP request object
     * @return JWT Token string, returns null if not exists
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // Get Bearer Token from Authorization request header
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Remove "Bearer " prefix, keep only Token part
            return bearerToken.substring(7);
        }
        return null;
    }
}