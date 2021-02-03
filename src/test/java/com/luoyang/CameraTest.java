package com.luoyang;

import com.luoyang.entity.Camera;
import com.luoyang.service.CameraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CameraTest {

    @Autowired
    private CameraService cameraService;

    @Test
    public void testSave(){
        Camera camera = new Camera("127.0.0.1", "北京", "camera5", "path");
        cameraService.save(camera);
    }

    @Test
    public void testUpdate(){
        Camera camera = new Camera("127.0.0.1", "南京", "camera5", "path");
        cameraService.update(camera);
    }

    @Test
    public void testDelete(){
        cameraService.delete("127.0.0.1");
    }

    @Test
    public void testFindAll(){
        List<Camera> all = cameraService.findAll();
        all.forEach(camera -> {
            System.out.println(camera);
        });
    }

    @Test void testFindTotal(){
        Integer total = cameraService.findTotal();
        System.out.println(total);
    }

    @Test void testFindByPage(){
        List<Camera> page = cameraService.findByPage(1, 3);
        page.forEach(System.out::println);
    }

}
