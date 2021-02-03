package com.luoyang.controller;

import com.luoyang.entity.User;
import com.luoyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public Map<String,Object> save(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Map<String,Object> map= new HashMap<>();
        try{
            userService.save(user);
            map.put("status",true);
            map.put("msg","用户注册成功");
        }catch (Exception e){
            map.put("status",false);
            map.put("msg","用户注册失败");
        }
        return map;
    }

    @GetMapping("/login")
    public Map<String,Object> login(String username, String password, Model model){
        Map<String,Object> map= new HashMap<>();
        try{
            String id=userService.login(username,password);
            System.out.println(id);
            if(id!=null){
                map.put("status",true);
                map.put("msg","用户登录成功");
                model.addAttribute("user_id",id);
            }else{
                map.put("status",false);
                map.put("msg","用户名或密码错误！");
            }

        }catch (Exception e){
            map.put("status",false);
            map.put("msg","用户登录失败,请稍后重尝试！");
        }
        return map;
    }

}
