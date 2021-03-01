package com.luoyang.dao;

import com.luoyang.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileDAO {
    // 保存上传文件记录
    void save(UserFile userFile);
    // 根据登录用户id获取用户的文件列表
    List<UserFile> findFileList();
    // 根据id查询文件记录
    UserFile findById(String id);
    // 删除记录
    void delete(String id);
    // 分页查找
    List<UserFile> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    // 返回总记录数量
    Integer findTotals();
    // 返回存储文件内总大小
    Integer findTotalSize();
}
