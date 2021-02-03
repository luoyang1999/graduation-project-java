package com.luoyang.service;

import com.luoyang.entity.UserFile;

import java.util.List;


public interface FileService {
    // 根据登录用户id获取用户的文件列表
    List<UserFile> findFileList();
    // 保存上传文件记录
    void save(UserFile userFile);
    // 根据id查询文件记录
    UserFile findById(String id);
    // 删除记录
    void delete(String id);
    // 分页查找
    List<UserFile> findByPage(Integer pageNow, Integer rows);
    // 返回总记录数量
    Integer findTotals();
}
