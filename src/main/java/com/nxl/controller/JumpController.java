package com.nxl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JumpController {

    @GetMapping("/gotoSuccess")
    public String gotoSuccess(){
        return "success";
    }

}
