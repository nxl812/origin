package com.nxl.controller;

import com.nxl.pojo.GlobalException;
import com.nxl.pojo.Response;
import com.nxl.service.DateServiceI;
import com.nxl.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/date")
public class DateController {
    @Resource
    private DateServiceI dateServiceImpl;

    @GetMapping("/addWorkDay")
    public Response<String> addWorkDay(@RequestParam("d")String day,
                                       @RequestParam(value = "p",defaultValue = "yyyyMMdd") String pattern,
                                       @RequestParam("add")Integer add) throws GlobalException {
        String result = dateServiceImpl.addWorkDay(day,pattern,add);
        Response<String> response = ResponseUtil.buildSuccess("success", result);
        return response;
    }

    @GetMapping("/flushHoliday")
    public Response flushHoliday(){
        dateServiceImpl.flushHoliday();
        Response<Object> response = ResponseUtil.buildSuccess("success");
        return response;
    }
}
