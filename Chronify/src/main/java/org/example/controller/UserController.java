package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器
 *
 * 处理用户信息查询和更新等请求
 * 需要Token认证
 *
 * @author Chronify
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/profile")
    public Result getProfile() {
        log.info("获取用户信息");

        // 从当前请求中获取用户ID
        Long userId = CurrentUserUtil.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户信息
     * @return 更新结果
     */
    @PutMapping("/profile")
    public Result updateProfile(@RequestBody User user) {
        log.info("更新用户信息: {}", user);

        Long userId = CurrentUserUtil.getCurrentUserId();
        user.setId(userId);

        // 调用服务层更新用户信息
        userService.updateProfile(user);
        return Result.success("更新成功");
    }
}