package com.luoyang.service;

import com.luoyang.dao.AnalysisDAO;
import com.luoyang.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private AnalysisDAO analysisDAO;

    @Override
    public void save(Analysis analysis) {
        analysisDAO.save(analysis);
    }

    @Override
    public void saveOrc(String id, String hall_name, Date begin_time, Date end_time) {
        analysisDAO.saveOrc(id,hall_name,begin_time,end_time);
    }

    @Override
    public void saveOrcFile(String id, String orc_filename) {
        analysisDAO.saveOrcFile(id,orc_filename);
    }

    @Override
    public void saveWordcardFile(String id, String wordcard_filename) {
        analysisDAO.saveWordcardFile(id,wordcard_filename);
    }

    @Override
    public void saveActionFile(String id, String action_filename) {
        analysisDAO.saveActionFile(id,action_filename);
    }

    @Override
    public void saveVideo(String id, String video_filename) {
        analysisDAO.saveVideo(id,video_filename);
    }

    @Override
    public void deleteAnalysis(String id) {
        analysisDAO.deleteAnalysis(id);
    }

    @Override
    public List<Analysis> findAll() {
        return analysisDAO.findAll();
    }

    @Override
    public Analysis findById(String id) {
        return analysisDAO.findById(id);
    }

    @Override
    public Integer findAnalysisTotal() {
        return analysisDAO.findAnalysisTotal();
    }

    @Override
    public Integer findFileTotal() {
        return analysisDAO.findFileTotal();
    }

    @Override
    public List<Analysis> findAnalysisByPage(Integer pageNow, Integer rows) {
        Integer start = (pageNow - 1) * rows;
        return analysisDAO.findAnalysisByPage(start, rows);
    }

    @Override
    public List<Map<String,Object>> findFileByPage(Integer pageNow, Integer rows){
        Integer start = (pageNow - 1) * rows;
        List<Map<String,Object>> mapList = analysisDAO.findFileByPage(start, rows);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(Map<String,Object> map : mapList){
            Date upload_time = (Date)map.get("upload_time");
            map.put("upload_time",f.format(upload_time));
        }
        return mapList;
    }
}
