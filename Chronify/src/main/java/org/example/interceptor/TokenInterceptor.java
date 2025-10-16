package org.example.interceptor;

import org.example.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Token认证拦截器
 *
 * 拦截需要认证的API请求，验证JWT Token的有效性
 * 解析用户信息并存入请求属性中供后续使用
 *
 * @author Chronify
 * @since 1.0.0
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行拦截验证
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  处理器对象
     * @return true-继续处理，false-中断处理
     * @throws Exception 处理异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理OPTIONS请求（CORS预检请求）
        // OPTIONS请求不需要进行Token验证
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 从请求头中获取Token
        String token = getTokenFromRequest(request);

        // 检查Token是否存在
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"未提供认证token\"}");
            return false;
        }

        // 验证Token有效性
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"token无效或已过期\"}");
            return false;
        }

        // 解析Token中的用户信息
        Long userId = JwtUtil.getUserIdFromToken(token);
        String username = JwtUtil.getUsernameFromToken(token);

        // 将用户信息存入request attribute，方便后续Controller获取
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);

        return true;
    }

    /**
     * 从HTTP请求中提取JWT Token
     *
     * @param request HTTP请求对象
     * @return JWT Token字符串，如果不存在则返回null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 从Authorization请求头中获取Bearer Token
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // 移除"Bearer "前缀，只保留Token部分
            return bearerToken.substring(7);
        }
        return null;
    }
}