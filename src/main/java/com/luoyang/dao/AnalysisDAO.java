package com.luoyang.dao;

import com.luoyang.entity.Analysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface AnalysisDAO {
    // 新增一个分析任务
    void save(Analysis analysis);
    // 保存orc功能识别的结果
    void saveOrc(@Param("id") String id,@Param("hall_name") String hall_name,@Param("begin_time") Date begin_time,
                 @Param("end_time") Date end_time);
    // 保存orc生成文件信息
    void saveOrcFile(@Param("id") String id,@Param("orc_filename")String orc_filename);
    // 保存wordcard生成文件信息
    void saveWordcardFile(@Param("id") String id,@Param("wordcard_filename") String wordcard_filename);
    // 保存action生成文件信息
    void saveActionFile(@Param("id") String id,@Param("action_filename")String action_filename);
    // 保存最终生成的视频文件信息
    void saveVideo(@Param("id") String id,@Param("video_filename")String video_filename);

    // 删除分析结果
    void deleteAnalysis(String id);

    // 查看所有分析结果
    List<Analysis> findAll();

    // 根据id查找某个分析结果
    Analysis findById(String id);

    // 返回分析的总条数
    Integer findAnalysisTotal();

    // 返回总条数
    Integer findFileTotal();

    // 分页查找
    List<Analysis> findAnalysisByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    // 返回视频类型的文件
    List<Map<String,Object>> findFileByPage(@Param("start") Integer start, @Param("rows") Integer rows);

}
