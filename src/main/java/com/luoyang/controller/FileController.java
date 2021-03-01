package com.luoyang.controller;

import com.luoyang.entity.UserFile;
import com.luoyang.service.FileService;
import com.luoyang.utils.NonStaticResourceHttpRequestHandler;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("file")
@CrossOrigin
@AllArgsConstructor
public class FileController {

    @Autowired
    private FileService fileService;

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    // 上传文件处理 并保存文件信息到数据库
    @PostMapping("upload")
    public void upload(MultipartFile aaa, Model model) throws IOException {
        // 获取上传用户id
        String user_id = (String)model.getAttribute("user_id");
        System.out.println(user_id);
        // 获取文件的原始名称
        String oldFileName = aaa.getOriginalFilename();
        String extension = "." + FilenameUtils.getExtension(oldFileName);
        // 生成随机字符串
        String uuid = UUID.randomUUID().toString().substring(30);
        // 生成新的文件名称
        String newFileName =
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + uuid+ extension;
        // 文件大小
        int size = (int)aaa.getSize();
        // 根据地址和日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + dateFormat + "/" + oldFileName.split("\\.")[0] + "-"+ uuid;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists()) dateDir.mkdirs();
        // 处理文件上传
        aaa.transferTo(new File(dateDir, newFileName));

        // 将文件信息放入数据库
        UserFile userFile = new UserFile();
        userFile.setId(UUID.randomUUID().toString())
                .setOld_name(oldFileName)
                .setNew_name(newFileName)
                .setExt(extension)
                .setSize(size)
                .setPath("/files/"+dateFormat + "/" + oldFileName.split("\\.")[0]+ "-"+ uuid);

        fileService.save(userFile);
    }

    // 文件下载
    @GetMapping("download")
    public void download(@RequestParam String id, HttpServletResponse response) throws IOException {

        UserFile userFile = fileService.findById(id);
        System.out.println(userFile);

        // 根据文件名称和路径 获取文件输入流
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static" + userFile.getPath();
        FileInputStream is = new FileInputStream(new File(realPath, userFile.getNew_name()));
        // 设置附件下载
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(userFile.getOld_name(),"utf8"));
//        response.setContentType("multipart/form-data");
        // 获得相应输出流
        ServletOutputStream os = response.getOutputStream();
        // 文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    // 视频播放
    @GetMapping("/video")
    public void videoPreview(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserFile userFile = fileService.findById(id);

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

    // 查询文件列表
    @GetMapping("list")
    @ResponseBody
    public List<UserFile> filelist(){
        return fileService.findFileList();
    }

    @GetMapping("findByPage")
    public Map<String,Object> findByPage(Integer pageNow, Integer pageSize){
        HashMap<String, Object> result = new HashMap<>();
        pageNow = pageNow==null?1:pageNow;
        pageSize = pageSize==null?6:pageSize;
        List<UserFile> files = fileService.findByPage(pageNow, pageSize);
        Integer totals = fileService.findTotals();
        result.put("files",files);
        result.put("total",totals);
        return result;
    }

    // 返回文件总数量
    @GetMapping("getNum")
    public int getNum(){
        return fileService.findTotals();
    }

    // 返回文件总大小
    @GetMapping("findTotalSize")
    public int getTotalSize(){
        return fileService.findTotalSize();
    }

    // 文件删除
    @GetMapping("delete")
    public Map<String,Object> delete(String id) throws FileNotFoundException {
        Map<String,Object> map = new HashMap<>();
        try{
            //根据id查询信息
            UserFile userFile = fileService.findById(id);
            //删除文件
            String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
            File file = new File(realPath, userFile.getNew_name());
            if(file.exists()){
                file.delete();
                // 删除数据库信息
                fileService.delete(id);
                map.put("success",true);
                map.put("msg","删除成功");
            }else {
                map.put("success",false);
                map.put("msg","删除失败：文件不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","删除失败：抛出异常 "+e.getMessage());
        }
        return map;
    }


}
