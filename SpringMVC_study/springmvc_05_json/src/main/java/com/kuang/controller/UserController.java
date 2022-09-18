package com.kuang.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kuang.pojo.User;
import com.kuang.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @RequestMapping("/j1")
    @ResponseBody//不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        User user=new User("小明",3,"男");
        String str = mapper.writeValueAsString(user);
        return str;
    }
    @RequestMapping("/j2")
    @ResponseBody//不会走视图解析器，会直接返回一个字符串
    public String json2() throws JsonProcessingException {
        List<User> users = new ArrayList<User>();
        User user1=new User("小明1",3,"男");
        User user2=new User("小明2",3,"男");
        User user3=new User("小明3",3,"男");
        User user4=new User("小明4",3,"男");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return JsonUtils.getJson(users);
    }
    @RequestMapping("/j3")
    @ResponseBody//不会走视图解析器，会直接返回一个字符串
    public String json3() throws JsonProcessingException {
        Date date=new Date();
        return JsonUtils.getJson(date);
    }
    @RequestMapping("/j4")
    @ResponseBody
    public String json4(){
        List<User> users = new ArrayList<User>();
        User user1=new User("小明1",3,"男");
        User user2=new User("小明2",3,"男");
        User user3=new User("小明3",3,"男");
        User user4=new User("小明4",3,"男");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        String str = JSON.toJSONString(users);
        return str;
    }
}
