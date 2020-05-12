package com.nxl.config;

import com.nxl.pojo.GlobalException;
import com.nxl.pojo.Response;
import com.nxl.util.ResponseUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    @Order(1)
    public Response catchShiroException(AuthorizationException e){
        //这是没有权限的异常，mdd
        return ResponseUtil.buildFail(202,"没的权限");
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Order(2)
    public Response catchGlobalException(Exception e){
        if (e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return ResponseUtil.buildFail(ex.getCode(),ex.getMsg());
        } else{
            return ResponseUtil.buildFail(500,e.getMessage());
        }
    }

}
