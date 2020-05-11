package com.nxl.util;

import com.nxl.pojo.Response;

public class ResponseUtil<T> {

    public static<T> Response<T> buildSuccess(String msg){
        Response response = new Response<>();
        response.setCode(200).setMsg(msg).setSuccess(true);
        return response;
    }
    public static<T> Response<T> buildSuccess(String msg,T t){
        Response<T> response = buildSuccess(msg);
        response.setT(t);
        return response;
    }

    public static<T> Response<T> buildFail(Integer code,String msg){
        Response response = new Response<>();
        response.setCode(code).setMsg(msg).setSuccess(false);
        return response;
    }
    public static<T> Response<T> buildFail(Integer code,String msg,T t){
        Response response = buildFail(code,msg);
        response.setT(t);
        return response;
    }

}
