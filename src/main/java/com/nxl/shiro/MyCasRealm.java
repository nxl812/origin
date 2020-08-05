package com.nxl.shiro;

import com.nxl.config.CasProperties;
import com.nxl.dao.mybatis.UserAccountMapper;
import com.nxl.pojo.mybatis.UserAccount;
import com.nxl.pojo.mybatis.UserAccountExample;
import com.nxl.service.LoginServiceI;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Deprecated
public class MyCasRealm extends CasRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyCasRealm.class);
    //cas需要单独服务，不想弄了
    //妈蛋，干！
    //cas搞定了，盐值加密的
    //https://www.cnblogs.com/funyoung/p/9242220.html
//    https://github.com/apereo/cas-overlay-template/tree/5.3
    //https://www.cnblogs.com/flying607/p/7605158.html

    // cas server地址
    @Value("${cas.casServerUrlPrefix}")
    public String casServerUrlPrefix;
    // 登录成功地址
    @Value("${cas.loginSuccessUrl}")
    public String loginSuccessUrl;
    @Resource
    CasProperties casProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    @Resource
    private LoginServiceI loginServiceImpl;
    @PostConstruct
    public void initProperty() {
        super.setCasServerUrlPrefix(casServerUrlPrefix);
        // 客户端回调地址
        // setCasService(loginSuccessUrl);
    }

    //这个方法用于加载权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
//        UserAccount users = (UserAccount) super.getAvailablePrincipal(arg0);
//        MenuService menuService = applicationContext.getBean(MenuService.class);
//        Set<String> perms = menuService.listPerms(users.getUserId());
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(perms);
//        super.onLogout(arg0);
//        logger.info("info:-------------->" + perms);
//        return info;
        return super.doGetAuthorizationInfo(arg0);
    }


    //这个方法用于认证用户身份
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }

        String ticket = (String) casToken.getCredentials();
        if (ticket == null || ticket.trim().length() == 0) {
            return null;
        }

        TicketValidator ticketValidator = ensureTicketValidator();
        try {
            // contact CAS server to validate service ticket
            String url = casProperties.getShiroServerUrlPrefix() + casProperties.getCasFilterUrlPattern();

            //注意这里大坑，稍后说明
            Assertion casAssertion = ticketValidator.validate(ticket, url);
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String username = casPrincipal.getName();

            UserAccountMapper userMapper = applicationContext.getBean(UserAccountMapper.class);
            // 查询用户信息
            UserAccountExample example =new UserAccountExample();
            example.createCriteria().andUserNameEqualTo(username);
            List<UserAccount> userAccounts = userMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(userAccounts)){
                UserAccount userAccount = userAccounts.get(0);

                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userAccount, ticket, getName());
                logger.info("info=" + info.toString());
                return info;
            } else {
                throw new UnknownAccountException("账号或者密码不正确");
            }

        } catch (TicketValidationException e) {
            e.printStackTrace();
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }

    }
}