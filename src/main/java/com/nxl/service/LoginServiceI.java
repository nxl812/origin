package com.nxl.service;

import com.nxl.pojo.UserDemo;
import com.nxl.pojo.mybatis.User;

public interface LoginServiceI {
    UserDemo getUserByName(String name);
}
