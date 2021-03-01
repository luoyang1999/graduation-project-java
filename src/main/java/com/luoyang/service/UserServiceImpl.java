package com.luoyang.service;

import com.luoyang.dao.UserDAO;
import com.luoyang.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreate_time(new Date());
        userDAO.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String login(String username, String password) {
        return userDAO.login(username,password);
    }

    @Override
    public void setTime(String username) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = f.format(new Date());
        userDAO.setTime(username, new Date());
    }

    @Override
    public String getTime(String username) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return f.format(userDAO.getTime(username));
    }

    @Override
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Override
    public void delete(String id) {
        userDAO.delete(id);
    }

    @Override
    public void reset(String id,String password) {
//        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        userDAO.reset(id,password);
    }

    @Override
    public int fixPassword(String username, String oldPassword, String newPassword) {
        if(oldPassword.equals(newPassword)){
            return -1;
        }
        if(newPassword==null){
            return -2;
        }
        if(userDAO.fixPassword(username,oldPassword,newPassword)==1){
            return 0;
        }else {
            return -2;
        }
    }
}
