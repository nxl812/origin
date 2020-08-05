package com.nxl.service;

import com.nxl.pojo.mybatis.User;

import java.util.List;

public interface UserServiceI {
    List<User> getAllUser();

    void getUserDynamic();

}
