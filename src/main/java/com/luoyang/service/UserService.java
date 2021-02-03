package com.luoyang.service;

import com.luoyang.entity.User;

public interface UserService {
    void save(User user);
    String login(String username,String password);
}
