package com.nxl.util;

import com.nxl.pojo.Response;

public class ResponseUtil<T> {

    public static<t> Response<t> buildSuccess(String msg){
        Response response = new Response<>();
        response.setCode(200).setMsg(msg).setSuccess(true);
        return response;
    }
    public static<t> Response<t> buildSuccess(String msg,t t1){
        Response<t> response = buildSuccess(msg);
        response.setT(t1);
        return response;
    }

    public static<t> Response<t> buildFail(Integer code,String msg){
        Response<t> response = new Response<>();
        response.setCode(code).setMsg(msg).setSuccess(false);
        return response;
    }
    public static<t> Response<t> buildFail(Integer code,String msg,t t1){
        Response<t> response = buildFail(code,msg);
        response.setT(t1);
        return response;
    }

}
