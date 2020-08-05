package com.nxl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//@Component
//@ConfigurationProperties(prefix = "cas")
@Deprecated
public class CasProperties {
    @Value("${cas.casServerUrlPrefix}")
    public static   String casServerUrlPrefix;
    // Cas登录页面地址
    @Value("${cas.casLoginUrl}")
    public static String casLoginUrl;
    // Cas登出页面地址
    @Value("${cas.casLogoutUrl}")
    public static String casLogoutUrl;
    // 当前工程对外提供的服务地址
    @Value("${cas.shiroServerUrlPrefix}")
    public static String shiroServerUrlPrefix;
    // casFilter UrlPattern
    @Value("${cas.casFilterUrlPattern}")
    public static String casFilterUrlPattern;
    // 登录地址
    @Value("${cas.loginUrl}")
    public static String loginUrl;
    // 登出地址
    @Value("${cas.logoutUrl}")
    public static String logoutUrl;
    // 登录成功地址
    @Value("${cas.loginSuccessUrl}")
    public static String loginSuccessUrl;
    // 权限认证失败跳转地址
    @Value("${cas.unauthorizedUrl}")
    public static String unauthorizedUrl;


    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasLoginUrl() {
        return casLoginUrl;
    }

    public void setCasLoginUrl(String casLoginUrl) {
        this.casLoginUrl = casLoginUrl;
    }

    public String getCasLogoutUrl() {
        return casLogoutUrl;
    }

    public void setCasLogoutUrl(String casLogoutUrl) {
        this.casLogoutUrl = casLogoutUrl;
    }

    public String getShiroServerUrlPrefix() {
        return shiroServerUrlPrefix;
    }

    public void setShiroServerUrlPrefix(String shiroServerUrlPrefix) {
        this.shiroServerUrlPrefix = shiroServerUrlPrefix;
    }

    public String getCasFilterUrlPattern() {
        return casFilterUrlPattern;
    }

    public void setCasFilterUrlPattern(String casFilterUrlPattern) {
        this.casFilterUrlPattern = casFilterUrlPattern;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }
}


