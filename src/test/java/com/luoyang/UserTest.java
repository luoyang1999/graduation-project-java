package com.luoyang;

import com.luoyang.entity.User;
import com.luoyang.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveTest(){
        User user = new User();
        user.setUsername("luoyang");
        user.setPassword("123456");
        userService.save(user);
    }

    @Test
    public void loginTest(){
        String user_id = userService.login("luoyang","123456");
        System.out.println(user_id);
    }
}
