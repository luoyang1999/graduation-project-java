package com.luoyang.controller;

import com.luoyang.entity.User;
import com.luoyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("user")
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
            userService.setTime(username);
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
                String login_time =userService.getTime(username);
                map.put("status",true);
                map.put("msg","用户登录成功");
                map.put("upload_time",login_time);
                model.addAttribute("user_id",id);
                userService.setTime(username);
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

    @GetMapping("/userList")
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/delete")
    public Map<String,Object> delete(String id){
        Map<String,Object> map= new HashMap<>();
        try{
            userService.delete(id);
            map.put("status",true);
            map.put("msg","用户删除成功");
        }catch (Exception e){
            map.put("status",false);
            map.put("msg","用户删除失败");
        }
        return map;
    }

    @GetMapping("/reset")
    public Map<String,Object> reset(String id,String password){
        Map<String,Object> map= new HashMap<>();
        try{
            userService.reset(id,password);
            map.put("status",true);
            map.put("msg","用户重置成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("status",false);
            map.put("msg","用户重置失败");
        }
        return map;
    }

    @GetMapping("fixPassword")
    public Map<String,Object> fixPassword(String username,String oldPassword,String newPassword){
        Map<String,Object> map = new HashMap<>();
        try{
            int result = userService.fixPassword(username,oldPassword,newPassword);
            if(result==-1){
                map.put("status",false);
                map.put("msg","新旧密码一致");
            }else if(result == -2){
                map.put("status",false);
                map.put("msg","密码错误，请重新输出");
            }else {
                map.put("status",true);
                map.put("msg","密码修改成功");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("status",false);
            map.put("msg","密码重置失败");
        }
        return map;
    }


}
