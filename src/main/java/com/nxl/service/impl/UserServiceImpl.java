package com.nxl.service.impl;

import com.nxl.dao.mybatis.UserMapper;
import com.nxl.pojo.mybatis.User;
import com.nxl.service.UserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {

        return userMapper.selectAll();
    }
}
