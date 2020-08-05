package com.nxl.shiro;

import com.google.gson.Gson;
import com.nxl.pojo.PermissionDemo;
import com.nxl.pojo.RoleDemo;
import com.nxl.pojo.UserDemo;
import com.nxl.service.LoginServiceI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Resource
    private LoginServiceI loginServiceImpl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //权限的
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        UserDemo user = loginServiceImpl.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<RoleDemo> roleSet = user.getRoleSet();

        for (RoleDemo role : roleSet) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRole());
            //添加权限
            for (PermissionDemo permissions : role.getPermissionsSet()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //登录的，/login会跳转这里
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        UserDemo user = loginServiceImpl.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setTimeout(30 * 60 * 1000);
            Gson gson = new Gson();
            String userStr = gson.toJson(gson);
            session.setAttribute("user", userStr);
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
