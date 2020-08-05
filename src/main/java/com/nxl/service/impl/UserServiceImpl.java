package com.nxl.service.impl;

import com.nxl.dao.mybatis.UserMapper;
import com.nxl.pojo.mybatis.User;
import com.nxl.service.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {

        return userMapper.selectAll();
    }

    @Override
    public void getUserDynamic() {
        String tableName = "user";
        User user = userMapper.getUserDynamic(tableName,1);
        logger.info("getUserDynamic,result:{}",user.toString());
    }
}
