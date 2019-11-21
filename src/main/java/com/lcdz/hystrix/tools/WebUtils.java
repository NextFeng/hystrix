package com.lcdz.hystrix.tools;


import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class WebUtils {

    public static Calendar calendar = Calendar.getInstance();

    /**
     * 获取前台提交的参数
     *
     * @return
     */
    public static Map getParameterMap(HttpServletRequest request) {
        Map parametersMap = new HashMap();
        Enumeration paramters = request.getParameterNames();
        while (paramters.hasMoreElements()) {
            String name = (String) paramters.nextElement();
            String[] values = request.getParameterValues(name);
            String value = "";
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    value += values[i] + ',';
                }
                value = value.substring(0, value.length() - 1);
                // value=value.replaceAll(".*([';]+|(--)+).*", " ");
            }
            parametersMap.put(name, value.trim());
        }
        return parametersMap;
    }


    /**
     * 获取前台提交的参数
     *
     * @return
     */
    public static Map getParameterMapWithPage(HttpServletRequest request) {
        Map parametersMap = getParameterMap(request);
        if(StringUtils.isEmpty(parametersMap.get("pageNum"))){
            parametersMap.put("pageNum","1");
        }
        if(StringUtils.isEmpty(parametersMap.get("pageSize"))){
            parametersMap.put("pageSize","8");
        }
        return parametersMap;
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getMxDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        return df.format(new Date());
    }


    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }


    public static String getYesday(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date date = cal.getTime();
        return df.format(date);
    }


    public static Map<String,String> toStingMap(Map<String,Object> paramap){
        Map<String,String> remap = new HashMap<>();
        for(String key : paramap.keySet()){
            remap.put(key,String.valueOf(paramap.get(key)));
        }
        return remap;
    }

    public static String getEid(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + String.valueOf(Math.round(Math.random()*899999+100000));
    }

    /**
     * 获取输入流
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getStream(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }
}
