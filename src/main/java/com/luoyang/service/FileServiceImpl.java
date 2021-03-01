package com.luoyang.service;

import com.luoyang.dao.FileDAO;
import com.luoyang.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService{

    @Autowired
    private FileDAO fileDAO;

    @Override
    public List<UserFile> findFileList() {
        return fileDAO.findFileList();
    }

    @Override
    public void save(UserFile userFile) {
        userFile.setUpload_time(new Date());
        fileDAO.save(userFile);
    }

    @Override
    public UserFile findById(String id) {
        return fileDAO.findById(id);
    }

    @Override
    public void delete(String id) {
        fileDAO.delete(id);
    }

    @Override
    public List<UserFile> findByPage(Integer pageNow, Integer rows) {
        Integer start = (pageNow - 1) * rows;
        return fileDAO.findByPage(start, rows);
    }

    @Override
    public Integer findTotals() {
        return fileDAO.findTotals();
    }

    @Override
    public Integer findTotalSize() {
        return fileDAO.findTotalSize();
    }
}
