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

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User loginUser) {
        log.info("用户登录: {}", loginUser.getUsername());

        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 生成JWT token
        String token = JwtUtil.generateToken(user.getUsername(), user.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return Result.success(data);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User registerUser) {
        log.info("用户注册: {}", registerUser.getUsername());

        if (registerUser.getUsername() == null || registerUser.getPassword() == null) {
            return Result.error("用户名和密码不能为空");
        }

        userService.register(registerUser.getUsername(), registerUser.getPassword());
        return Result.success();
    }
}