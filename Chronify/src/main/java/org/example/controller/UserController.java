package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User Information Controller
 *
 * Handles user information query and update requests
 * Requires Token authentication
 *
 * @author Chronify
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    /**
     * User service layer
     */
    @Autowired
    private UserService userService;

    /**
     * Get current user information
     *
     * @return User information
     */
    @GetMapping("/profile")
    public Result getProfile() {
        log.info("Get user information");

        // Get user ID from current request
        Long userId = CurrentUserUtil.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    /**
     * Update user information
     *
     * @param user User information to be updated
     * @return Update result
     */
    @PutMapping("/profile")
    public Result updateProfile(@RequestBody User user) {
        log.info("Update user information: {}", user);

        Long userId = CurrentUserUtil.getCurrentUserId();
        user.setId(userId);

        // Call service layer to update user information
        userService.updateProfile(user);
        return Result.success("Update successful");
    }
}