package com.kuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller//代表这个类会被Spring接管，被@Controller所注解的类中的所有方法，如果返回值为String，且有具体的页面可跳转，那么就会被视图解析器解析
public class ControllerTest2 {
//    视图可以复用
    @RequestMapping("/t1")
    public String test1(Model model){
        model.addAttribute("msg","Hello Controller Test2!!");
        return "test";
    }
    @RequestMapping("/t3")
    public String test2(Model model){
        model.addAttribute("msg","Test3！！！");
        return "test";
    }
}
