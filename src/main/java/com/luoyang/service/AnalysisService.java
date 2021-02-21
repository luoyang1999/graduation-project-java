package com.luoyang.service;

import com.luoyang.entity.Analysis;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AnalysisService {
    // 保存上传文件记录
    void save(Analysis analysis);
    // 保存hallname begintime endtime
    void saveOrc(String id, String hall_name, Date begin_time, Date end_time);
    // 保存orc生成文件信息
    void saveOrcFile(String id,String orc_filename);
    // 保存wordcard生成文件信息
    void saveWordcardFile(String id, String wordcard_filename);
    // 保存action生成文件信息
    void saveActionFile(String id, String action_filename);
    // 保存最终生成的视频文件信息
    void saveVideo(String id, String video_filename);

    // 删除分析结果
    void deleteAnalysis(String id);

    // 查看所有分析结果
    List<Analysis> findAll();
    // 分页查找
    List<Analysis> findAnalysisByPage(Integer pageNow, Integer rows);
    // 返回分析结果总条数
    Integer findAnalysisTotal();
    // 返回视频文件总的数量
    Integer findFileTotal();
    // 根据id查找某个分析结果
    Analysis findById(String id);
    // 提供视频类型可分析的文件
    List<Map<String,Object>> findFileByPage(Integer pageNow, Integer rows);
}
