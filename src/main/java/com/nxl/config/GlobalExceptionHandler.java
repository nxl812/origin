package com.nxl.config;

import com.nxl.pojo.GlobalException;
import com.nxl.pojo.Response;
import com.nxl.util.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response catchGlobalException(Exception e){
        if (e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return ResponseUtil.buildFail(ex.getCode(),ex.getMsg());
        } else{
            return ResponseUtil.buildFail(500,e.getMessage());
        }
    }

}
