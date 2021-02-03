package com.luoyang.controller;

import com.luoyang.entity.Camera;
import com.luoyang.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @GetMapping("findAll")
    public List<Camera> findAll(){
        return cameraService.findAll();
    }

    @GetMapping("findByPage")
    public Map<String,Object> findByPage(Integer pageNow, Integer pageSize){
        HashMap<String, Object> result = new HashMap<>();
        pageNow = pageNow==null?1:pageNow;
        pageSize = pageSize==null?8:pageSize;
        List<Camera> cameras = cameraService.findByPage(pageNow, pageSize);
        Integer totals = cameraService.findTotal();
        result.put("cameras",cameras);
        result.put("total",totals);
        return result;
    }

    // 删除记录
    @GetMapping("delete")
    public Map<String,Object> delete(String ip){
        Map<String,Object> map = new HashMap<>();
        try{
            cameraService.delete(ip);
            map.put("success",true);
            map.put("msg","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","删除失败"+e.getMessage());
        }
        return map;
    }

    // 更新记录
    @PostMapping("update")
    public Map<String,Object> update(@RequestBody Camera camera){
        System.out.println(camera);
        Map<String,Object> map = new HashMap<>();
        try{
            cameraService.update(camera);
            map.put("success",true);
            map.put("msg","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","修改失败"+e.getMessage());
        }
        return map;
    }

    // 添加记录
    @PostMapping("save")
    public Map<String,Object> save(@RequestBody Camera camera){
        Map<String,Object> map = new HashMap<>();
        try{
            cameraService.save(camera);
            map.put("success",true);
            map.put("msg","添加成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","添加失败"+e.getMessage());
        }
        return map;
    }
}
