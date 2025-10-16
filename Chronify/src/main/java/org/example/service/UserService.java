package org.example.service;

import org.example.pojo.User;

/**
 * 用户服务接口
 *
 * 定义用户相关的业务逻辑操作
 *
 * @author Chronify
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息，登录失败返回null
     */
    User login(String username, String password);

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    void register(String username, String password);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户信息
     */
    void updateProfile(User user);
}