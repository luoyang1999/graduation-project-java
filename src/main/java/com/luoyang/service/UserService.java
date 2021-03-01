package com.luoyang.service;

import com.luoyang.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    String login(String username,String password);
    void setTime(String username);
    String getTime(String username);
    List<User> getUserList();
    void delete(String id);
    void reset(String id,String password);
    int fixPassword(String username,String oldPassword,String newPassword);
}
