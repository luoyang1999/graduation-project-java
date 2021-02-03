package com.luoyang.dao;

import com.luoyang.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDAO {
    void save(User user);
    String login(@Param("username") String username, @Param("password") String password);
}
