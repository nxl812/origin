package com.nxl.config;

import com.nxl.shiro.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnExpression("'${using.shiro.redis}'=='true'")
public class ShiroRedisConfiguration {
    private static Logger logger = LoggerFactory.getLogger(ShiroRedisConfiguration.class);

    @Autowired
    private ShiroRedisConfig shiroRedisConfig;
    @Value("${shiro.sessionid.cookie.name:nxl_shiro_redis_cookie_prefix}")
    private String sessionIdCookieName;

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    @Bean(name = "RedisManager")
    public RedisManager redisManager() {
        if (StringUtils.isEmpty(shiroRedisConfig.getHost())) {
            throw new IllegalArgumentException("The config [spring.redisson.host] is blank.");
        }

        RedisManager redisManager = new RedisManager();
        redisManager.setHost(shiroRedisConfig.getHost());
        redisManager.setPort(shiroRedisConfig.getPort());
//        redisManager.setPassword(shiroRedisConfig.getPassword());
        redisManager.setExpire(shiroRedisConfig.getExpire() <= 0 ? 1800 : shiroRedisConfig.getExpire());// 配置过期时间
        redisManager.setTimeout(shiroRedisConfig.getTimeout());

        logger.info("配置redis连接设置 ########## " + shiroRedisConfig.getHost() + ":::" + shiroRedisConfig.getPort());

        return redisManager;
    }

    @Bean(name = "SessionDAO")
    SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean(name = "SessionManager")
    public SessionManager sessionManager() {
        NxlDefaultWebSessionManager sessionManager = new NxlDefaultWebSessionManager(sessionIdCookieName);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        map.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        map.put("/static/**", "anon");
        map.put("/ajaxLogin", "anon");
        map.put("/login", "anon");
        map.put("/**", "authc");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/unLogin");
        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        //shiroFilterFactoryBean.setUnauthorizedUrl("");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
