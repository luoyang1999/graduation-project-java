package com.luoyang.service;

import com.luoyang.entity.Camera;

import java.util.List;

public interface CameraService {
    void save(Camera camera);
    void delete(String ip);
    void update(Camera camera);
    List<Camera> findAll();
    List<Camera> findByPage(Integer pageNow, Integer rows);
    Integer findTotal();
}
