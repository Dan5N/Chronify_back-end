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
 * User Authentication Controller
 *
 * Handles user login, registration and other authentication related requests
 * Provides JWT Token generation functionality
 *
 * @author Chronify
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * User service layer
     */
    @Autowired
    private UserService userService;

    /**
     * User login
     *
     * @param loginUser Login user information (username and password)
     * @return Response result containing token and user information
     */
    @PostMapping("/login")
    public Result login(@RequestBody User loginUser) {
        log.info("User login: {}", loginUser.getUsername());

        // Call service layer to verify user login
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user == null) {
            // Login failed, username or password incorrect
            return Result.error("Username or password incorrect");
        }

        // Login successful, generate JWT token
        String token = JwtUtil.generateToken(user.getUsername(), user.getId());

        // Build return data
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);      // JWT Token
        data.put("user", user);        // User information

        return Result.success(data);
    }

    /**
     * User registration
     *
     * @param registerUser Registration user information (username and password)
     * @return Registration result response
     */
    @PostMapping("/register")
    public Result register(@RequestBody User registerUser) {
        log.info("User registration: {}", registerUser.getUsername());

        // Parameter validation
        if (registerUser.getUsername() == null || registerUser.getPassword() == null) {
            return Result.error("Username and password cannot be empty");
        }

        // Call service layer to register user
        userService.register(registerUser.getUsername(), registerUser.getPassword());
        return Result.success();
    }
}