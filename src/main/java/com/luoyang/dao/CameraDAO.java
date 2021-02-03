package com.luoyang.dao;

import com.luoyang.entity.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CameraDAO {
    void save(Camera camera);
    void delete(String ip);
    void update(Camera camera);
    List<Camera> findAll();
    List<Camera> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    Integer findTotal();
}
