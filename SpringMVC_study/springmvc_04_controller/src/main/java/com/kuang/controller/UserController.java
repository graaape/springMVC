package com.kuang.controller;

import com.kuang.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/t1")
    public String test1(@RequestParam("username") String name, Model model){
//        1.接收前端参数
        System.out.println("接收到前端的参数为："+name);
//        2.将返回的结果传递给前端
        model.addAttribute("msg",name);
//        3.视图跳转
        return "test";
    }
//    前端接收的是一个对象
    @RequestMapping("/t2")
    public String test2(User user){
        System.out.println(user);
        return "test";
    }
    @RequestMapping("/t3")
    public String test3(@RequestParam("username") String name,ModelMap map){
        map.addAttribute("name",name);
        System.out.println(name);
        return "test";
    }
}
