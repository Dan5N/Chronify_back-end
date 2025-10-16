package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Result getProfile() {
        log.info("获取用户信息");

        Long userId = CurrentUserUtil.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result updateProfile(@RequestBody User user) {
        log.info("更新用户信息: {}", user);

        Long userId = CurrentUserUtil.getCurrentUserId();
        // TODO: 实现用户信息更新逻辑
        return Result.success();
    }
}