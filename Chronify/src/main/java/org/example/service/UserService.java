package org.example.service;

import org.example.pojo.User;

public interface UserService {

    User login(String username, String password);

    void register(String username, String password);

    User getUserInfo(Long userId);
}