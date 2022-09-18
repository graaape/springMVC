package com.kuang.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtils {
    public static String getJson(Object obj){
        return getJson(obj,"yyyy-MM-dd HH:mm:ss");
    }
    public static String getJson(Object obj,String dateFormat){
        ObjectMapper mapper=new ObjectMapper();
        //使用ObjectMapper格式化输出，关闭时间戳
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);
        //ObjectMapper时间解析的默认格式为Timestamp 时间戳
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
