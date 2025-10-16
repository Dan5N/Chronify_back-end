package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证控制器
 *
 * 处理用户登录、注册等认证相关请求
 * 提供JWT Token生成功能
 *
 * @author Chronify
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param loginUser 登录用户信息（用户名和密码）
     * @return 包含token和用户信息的响应结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody User loginUser) {
        log.info("用户登录: {}", loginUser.getUsername());

        // 调用服务层验证用户登录
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user == null) {
            // 登录失败，用户名或密码错误
            return Result.error("用户名或密码错误");
        }

        // 登录成功，生成JWT token
        String token = JwtUtil.generateToken(user.getUsername(), user.getId());

        // 构建返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);      // JWT Token
        data.put("user", user);        // 用户信息

        return Result.success(data);
    }

    /**
     * 用户注册
     *
     * @param registerUser 注册用户信息（用户名和密码）
     * @return 注册结果响应
     */
    @PostMapping("/register")
    public Result register(@RequestBody User registerUser) {
        log.info("用户注册: {}", registerUser.getUsername());

        // 参数校验
        if (registerUser.getUsername() == null || registerUser.getPassword() == null) {
            return Result.error("用户名和密码不能为空");
        }

        // 调用服务层注册用户
        userService.register(registerUser.getUsername(), registerUser.getPassword());
        return Result.success();
    }
}