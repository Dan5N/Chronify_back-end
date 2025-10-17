package org.example.service;

import org.example.pojo.User;

/**
 * User Service Interface
 *
 * Defines user-related business logic operations
 *
 * @author Chronify
 * @since 1.0.0
 */
public interface UserService {

    /**
     * User login
     *
     * @param username Username
     * @param password Password
     * @return User information, returns null if login fails
     */
    User login(String username, String password);

    /**
     * User registration
     *
     * @param username Username
     * @param password Password
     */
    void register(String username, String password);

    /**
     * Get user information
     *
     * @param userId User ID
     * @return User information
     */
    User getUserInfo(Long userId);

    /**
     * Update user information
     *
     * @param user User information to be updated
     */
    void updateProfile(User user);
}