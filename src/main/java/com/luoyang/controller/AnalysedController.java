package com.luoyang.controller;

import com.luoyang.entity.Analysis;
import com.luoyang.entity.UserFile;
import com.luoyang.service.AnalysisService;
import com.luoyang.service.FileService;
import com.luoyang.utils.NonStaticResourceHttpRequestHandler;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("analysed")
@CrossOrigin
@AllArgsConstructor
public class AnalysedController {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private FileService fileService;

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @GetMapping("findAnalysisByPage")
    @ResponseBody
    public Map<String,Object> findAnalysisByPage(Integer pageNow, Integer pageSize){
        HashMap<String, Object> result = new HashMap<>();
        pageNow = pageNow==null?1:pageNow;
        pageSize = pageSize==null?6:pageSize;
        List<Analysis> analysises = analysisService.findAnalysisByPage(pageNow, pageSize);
        Integer totals = analysisService.findAnalysisTotal();
        result.put("analysis",analysises);
        result.put("total",totals);
        return result;
    }

    @GetMapping("delete")
    public Map<String,Object> deleteById(@RequestParam String id){
        HashMap<String, Object> result = new HashMap<>();

        try{
            // 根据文件名称和路径 获取文件路径
            Analysis analysis = analysisService.findById(id);
            UserFile userFile = fileService.findById(analysis.getFile_id());
            String realPath = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath();

            // 删除分析输出的文件
            String message = "删除 ";
            // 对文件进行删除操作
            if(analysis.getOcr_filename()!=null){
                File file1 = new File(realPath,analysis.getOcr_filename());
                if(file1.exists()){
                    file1.delete();
                    message += "OCR文件 ";
                }
            }else if(analysis.getWordcard_filename()!=null){
                File file2 = new File(realPath,analysis.getWordcard_filename());
                if(file2.exists()){
                    file2.delete();
                    message += "工牌文件 ";
                }
            }else if(analysis.getAction_filename()!=null){
                File file3 = new File(realPath,analysis.getAction_filename());
                if(file3.exists()){
                    file3.delete();
                    message += "行为识别文件 ";
                }
            }else if(analysis.getVideo_filename()!=null){
                File file4 = new File(realPath,analysis.getVideo_filename());
                if(file4.exists()){
                    file4.delete();
                    message += "输出视频文件 ";
                }
            }
            analysisService.deleteAnalysis(id);
            message += "成功！";
            result.put("status",true);
            result.put("msg",message);

        }catch (Exception e){
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","删除失败：抛出异常 "+e.getMessage());
        }

        return result;
    }

    // function2 播放数据库中.mp4结尾的视频文件
    @GetMapping("playVideo")
    public void videoPreview(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Analysis analysis = analysisService.findById(id);
        UserFile userFile = fileService.findById(analysis.getFile_id());

        // 根据文件名称和路径 获取文件输入流
        String realPath = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath();

        Path filePath = Paths.get(realPath,analysis.getWordcard_filename());
        System.out.println(filePath);

        if (Files.exists(filePath)) {
            System.out.println("file exists");
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    // 文件下载
    @GetMapping("download")
    public void download(@RequestParam String id, HttpServletResponse response) throws IOException {

        Analysis analysis = analysisService.findById(id);
        UserFile userFile = fileService.findById(analysis.getFile_id());
        System.out.println(userFile);

        // 根据文件名称和路径 获取文件输入流
        String realPath = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath();
        FileInputStream is = new FileInputStream(new File(realPath, analysis.getWordcard_filename()));

        // 设置附件下载
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(userFile.getOld_name(),"utf8"));
        //response.setContentType("multipart/form-data");
        // 获得相应输出流
        ServletOutputStream os = response.getOutputStream();
        // 文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
}
