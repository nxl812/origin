package com.nxl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class JumpController {

    @GetMapping("/gotoSuccess")
    public String gotoSuccess(){
        return "success";
    }

    @GetMapping("/druidHtml")
    public String druidHtml(){
        return "redirect:/druid/login.html";
    }

}
