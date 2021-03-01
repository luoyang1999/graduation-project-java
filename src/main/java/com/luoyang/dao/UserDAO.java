package com.luoyang.dao;

import com.luoyang.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDAO {
    void save(User user);
    String login(@Param("username") String username, @Param("password") String password);
    void setTime(@Param("username") String username, @Param("login_time") Date date);
    Date getTime(String username);
    List<User> getUserList();
    void delete(String id);
    void reset(@Param("id")String id, @Param("password")String password);
    int fixPassword(@Param("username")String username, @Param("oldPassword")String oldPassword,
                     @Param("newPassword")String newPassword);
}
