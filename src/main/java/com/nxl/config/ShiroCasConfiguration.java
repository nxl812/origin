package com.nxl.config;

import com.nxl.shiro.MyCasRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
//@ConditionalOnExpression("'${using.shiro.cas}'=='true'")
@Deprecated
public class ShiroCasConfiguration {
    private static Logger logger = LoggerFactory.getLogger(ShiroCasConfiguration.class);

    //可选开始
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.shiro.cache.type}")
    private String cacheType;

    @Value("${server.session-timeout}")
    private int tomcatTimeout;
    //可选结束


//    @Bean(name = "myShiroCasRealm")
    public MyCasRealm myShiroCasRealm(EhCacheManager cacheManager) {
        MyCasRealm realm = new MyCasRealm();
        realm.setCacheManager(cacheManager);
        realm.setCasServerUrlPrefix(CasProperties.casServerUrlPrefix);
        // 客户端回调地址
        realm.setCasService(CasProperties.loginSuccessUrl);
        return realm;
    }


    //注册单点登出的listener
//    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    // 优先级需要高于Cas的Filter
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }


//    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        bean.addUrlPatterns("/");
        bean.setEnabled(true);
        return bean;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     *
     * @param
     * @return
     * @author SHANHY
     * @create 2016年1月13日
     */
//    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

//    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

//    @Bean(name = "securityManager")
    public SecurityManager getDefaultWebSecurityManager(MyCasRealm myCasRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myCasRealm);
        // 自定义缓存实现 使用redis，可选开始


//        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
//            dwsm.setCacheManager(rediscacheManager());
//        } else {
//            dwsm.setCacheManager(ehCacheManager());
//            logger.info("ehCachemanager--------->");
//        }

        // 自定义缓存实现 使用redis，可选结束


//  <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
        dwsm.setCacheManager(ehCacheManager());
        // 指定 SubjectFactory
        dwsm.setSubjectFactory(new CasSubjectFactory());
        return dwsm;
    }

//    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create 2016年1月14日
     */

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put(CasProperties.casFilterUrlPattern, "casFilter");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
//        filterChainDefinitionMap.put("/docs/**", "anon");
//        filterChainDefinitionMap.put("/druid/**", "anon");
//        filterChainDefinitionMap.put("/upload/**", "anon");
//        filterChainDefinitionMap.put("/files/**", "anon");
        //filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/blog", "anon");
//        filterChainDefinitionMap.put("/blog/open/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * CAS过滤器
     *
     * @return
     * @author SHANHY
     * @create 2016年1月17日
     */
//    @Bean(name = "casFilter")
    @Order(2)
    public CasFilter getCasFilter() {
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
        casFilter.setFailureUrl(CasProperties.loginUrl);// 我们选择认证失败后再打开登录页面
        return casFilter;
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @param
     * @param
     * @param
     * @return
     * @author SHANHY
     * @create 2016年1月14日
     */
//    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, CasFilter casFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(CasProperties.loginUrl);
        // 登录成功后要跳转的连接
//    shiroFilterFactoryBean.setSuccessUrl(CasProp.loginSuccessUrl);
//        shiroFilterFactoryBean.setUnauthorizedUrl(CasProperties.unauthorizedUrl);
        // 添加casFilter到shiroFilter中
        Map<String, Filter> filters = new HashMap<>();
        filters.put("casFilter", casFilter);
        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

//    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(cacheManager());
        return em;
    }

//    @Bean("cacheManager2")
    CacheManager cacheManager() {
        return CacheManager.create();
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
//    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//     * 配置shiro redisManager，可选
//     *
//     * @return
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setExpire(1800);// 配置缓存过期时间
//        //redisManager.setTimeout(1800);
//        redisManager.setPassword(password);
//        return redisManager;
//    }

//    /**
//     * cacheManager 缓存 redis实现，可选
//     * 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    @Bean
//    public RedisCacheManager rediscacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis，可选
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }

    //可选
//    @Bean
    public SessionDAO sessionDAO() {
//        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
//            return redisSessionDAO();
//        } else {
//            return new MemorySessionDAO();
//        }
        return new MemorySessionDAO();
    }

    /**
     * shiro session的管理，可选
     */
//    @Bean
//    public NxlDefaultWebSessionManager sessionManager() {
//        NxlDefaultWebSessionManager sessionManager = new NxlDefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
//        sessionManager.setSessionDAO(sessionDAO());
//        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//        listeners.add(new BDSessionListener());
//        sessionManager.setSessionListeners(listeners);
//        return sessionManager;
//    }


}
