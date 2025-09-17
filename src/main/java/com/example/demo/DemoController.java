package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class DemoController {
    @GetMapping("/hello2") // 전송 방식 GET
    public String hello(Model model) {
    model.addAttribute("data", " 방갑습니다."); // model 설정
    return "hello"; // hello.html 연결
}
    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }
    
}
