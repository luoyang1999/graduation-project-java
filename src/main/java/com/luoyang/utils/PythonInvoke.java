package com.luoyang.utils;

import com.luoyang.service.AnalysisService;
import com.luoyang.websocket.WebSocketServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class PythonInvoke implements Callable<Integer> {

    private final String args;          // 命令行执行指令
    private final File dir;             // 命令执行路径
    private final String fileName;      // 文件名称（路径）
    private final String type;          // 分析任务类型
    private final String analysisId;    // 分析任务ID
    private final AnalysisService analysisService;  // analysis服务层

    public PythonInvoke(String args, File dir, String fileName, String type,String analysisId,
                        AnalysisService analysisService){
        this.args = args;
        this.dir = dir;
        this.fileName = fileName;
        this.type = type;
        this.analysisId = analysisId;
        this.analysisService = analysisService;
    }

    @Override
    public Integer call() throws Exception {
        return invokePy();
    }

    public int invokePy() {
        int re;
        try {
            System.out.println("开始python脚本");
            Runtime rt=Runtime.getRuntime();
            Process process=rt.exec(args,null,dir);
            //防止Python输出中文时乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = error.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            error.close();
            re = process.waitFor();

            // 数据库进行信息存储
            if(re==0){
                System.out.println("正常结束python脚本");
                if(this.type.equals("OCR分析")){
                    this.analysisService.saveOrcFile(analysisId,fileName);
                }else if(this.type.equals("工牌正反识别")){
                    this.analysisService.saveWordcardFile(analysisId,"wordcard.txt");
                }else if(this.type.equals("人体行为识别")){
                    this.analysisService.saveActionFile(analysisId,"action.txt");
                }
                WebSocketServer.sendInfo("分析完成","111");
            }
            else{
                System.out.println("错误结束python脚本");
                WebSocketServer.sendInfo("分析出错","111");
            }

        } catch (Exception e) {
            re=-1;
            e.printStackTrace();
        }
        return re;
    }
}