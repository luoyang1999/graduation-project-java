package com.luoyang.service;

import com.luoyang.dao.CameraDAO;
import com.luoyang.entity.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CameraServiceImpl implements CameraService{

    @Autowired
    private CameraDAO cameraDAO;

    @Override
    public void save(Camera camera) {
        cameraDAO.save(camera);
    }

    @Override
    public void delete(String ip) {
        cameraDAO.delete(ip);
    }

    @Override
    public void update(Camera camera) {
        cameraDAO.update(camera);
    }

    @Override
    public List<Camera> findAll() {
        return cameraDAO.findAll();
    }

    @Override
    public List<Camera> findByPage(Integer pageNow, Integer rows) {
        Integer start = (pageNow - 1) * rows;
        return cameraDAO.findByPage(start, rows);
    }

    @Override
    public Integer findTotal() {
        return cameraDAO.findTotal();
    }
}
