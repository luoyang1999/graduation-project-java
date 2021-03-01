package com.luoyang.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchartsJson {
    public static ArrayList<String> txtToArrayList(String filename) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(filename);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader bf = new BufferedReader(inputReader);
            StringBuilder s=new StringBuilder();

            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                //System.out.println(str);
                arrayList.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static Map<String,Object> getWordcardJson(ArrayList<String> arrayList){
        Map<String,Object> map = new HashMap<>();
        map.put("type","bar");
        map.put("title","工牌正反位置示意");
        ArrayList<String> num = new ArrayList();
        ArrayList<Integer> state = new ArrayList();
        for(String str : arrayList){
            num.add(str.split(" ")[0]);
            if(str.split(" ")[1].equals("front"))
                state.add(1);
            else
                state.add(0);
        }
        map.put("labels",num);
        map.put("data",state);
        return map;
    }

    public static void main(String[] args) {
        String filename = "C:\\Users\\13216\\IdeaProjects\\ssm_springmvc\\surveillance\\target\\classes\\static" +
                "\\files\\2021-02-21\\example5-9dcdaa\\wordcard.txt";
        ArrayList<String> arrayList =txtToArrayList(filename);
        for(String word : arrayList){
            System.out.println(word);
        }
    }
}
