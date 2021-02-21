package com.luoyang.controller;

import com.luoyang.entity.Analysis;
import com.luoyang.entity.Submit;
import com.luoyang.entity.UserFile;
import com.luoyang.service.AnalysisService;
import com.luoyang.service.FileService;
import com.luoyang.utils.NonStaticResourceHttpRequestHandler;
import com.luoyang.utils.PythonInvoke;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("analysis")
@CrossOrigin
@AllArgsConstructor
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private FileService fileService;

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    // function1 读取数据库中.mp4结尾的视频文件
    @GetMapping("findFileByPage")
    @ResponseBody
    public Map<String,Object> findFileByPage(Integer pageNow, Integer pageSize){
        HashMap<String, Object> result = new HashMap<>();
        pageNow = pageNow==null?1:pageNow;
        pageSize = pageSize==null?6:pageSize;
        List<Map<String,Object>> files = analysisService.findFileByPage(pageNow, pageSize);
        Integer totals = analysisService.findFileTotal();
        result.put("files",files);
        result.put("total",totals);
        return result;
    }

    // function2 播放数据库中.mp4结尾的视频文件
    @GetMapping("playVideo")
    public void videoPreview(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserFile userFile = fileService.findById(id);
        System.out.println(userFile);

        // 根据文件名称和路径 获取文件输入流
        String realPath = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath();

        Path filePath = Paths.get(realPath,userFile.getNew_name());
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

    @ResponseBody
    @PostMapping("submitForm")
    public Map<String,Object> submitForm(@RequestBody Submit submit) throws IOException, ExecutionException, InterruptedException {
        // 将分析请求存储到数据库中
        Analysis analysis = new Analysis();
        analysis.setId(UUID.randomUUID().toString());
        analysis.setFile_id(submit.getFile_id());
        analysis.setHall_name(submit.getHall_name());
        analysis.setOld_name(submit.getName());
        analysisService.save(analysis);
        // 取得
        UserFile userFile = fileService.findById(submit.getFile_id());
        // 根据文件名称和路径 获取文件输入流
        String[] fileSplit = userFile.getNew_name().split("\\.");
        String fileNameInput = fileSplit[0] + "." + fileSplit[1];
        String fileNameOutput = fileSplit[0] + "x" + "." + fileSplit[1];
        String inputPath =
                ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath() + "/" + fileNameInput;
        String outputPath =
                ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + userFile.getPath();


        // 执行相应的python脚本
        for(String type : submit.getType()){
            switch (type) {
                case "OCR分析": {
                    String para = "cmd /c activate py38 && python JavaOrc.py" + " -i " + inputPath +
                            " -o " + outputPath + " -n " + submit.getHall_name();
                    System.out.println(para);
                    File dir = new File("C://Users/13216/PycharmProjects/TorchLearn/OrcRec");
                    FutureTask<Integer> futureTask = new FutureTask<>(new PythonInvoke(para, dir, type, fileNameOutput,
                            analysis.getId(), analysisService));
                    Thread t1 = new Thread(futureTask, "ocr");
                    t1.start();

                    break;
                }
                case "工牌正反识别": {
                    String para = "cmd /c activate && python JavaVideoOut.py" + " -i " + inputPath +
                            " -o " + outputPath;
                    System.out.println(para);
                    File dir = new File("C://Users/13216/PycharmProjects/TorchLearn/WorkCardRec");
                    FutureTask<Integer> futureTask = new FutureTask<>(new PythonInvoke(para, dir, type, fileNameOutput,
                            analysis.getId(), analysisService));
                    Thread t2 = new Thread(futureTask, "wordcard");
                    t2.start();

                    break;
                }
                case "人体行为识别":
                    System.out.println("人体行为识别python接口待完善");
                    break;
            }
        }

        System.out.println(submit);
        Map<String, Object> map = new HashMap<>();
        map.put("status",true);
        map.put("msg","成功");
        return map;
    }


}
