package com.nxl.controller;

import com.nxl.pojo.Response;
import com.nxl.pojo.UserDemo;
import com.nxl.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @PostMapping("/ajaxLogin")
    public Response ajaxLogin(UserDemo userDemo){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                userDemo.getName(),
                userDemo.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            return ResponseUtil.buildFail(201,"用户名密码错误");
        } catch (AuthorizationException e) {
            return ResponseUtil.buildFail(202,"没有权限");
        }
        return ResponseUtil.buildSuccess("登录成功");
    }

    @GetMapping("/login")
    public Response login(UserDemo userDemo){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                userDemo.getName(),
                userDemo.getPassword()
        );
        try {
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            return ResponseUtil.buildFail(201,"用户名密码错误");
        } catch (AuthorizationException e) {
            return ResponseUtil.buildFail(202,"没有权限");
        }
        return ResponseUtil.buildSuccess("登录成功");
    }


    @GetMapping("/unauth")
    public Response unauth(){
        return ResponseUtil.buildFail(203,"未登录");
    }


    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}