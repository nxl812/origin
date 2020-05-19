package com.nxl.controller;

import com.nxl.pojo.mybatis.User;
import com.nxl.service.UserServiceI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private UserServiceI userServiceImpl;

    @GetMapping("/test")
    public String test(){
        return "Hello World!";
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        List<User> retList =  userServiceImpl.getAllUser();
        return retList;
    }
}
