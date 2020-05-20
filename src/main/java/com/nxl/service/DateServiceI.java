package com.nxl.service;

import com.nxl.pojo.GlobalException;

public interface DateServiceI {
    void flushHoliday();

    String addWorkDay(String day, String pattern, Integer add) throws GlobalException;
}
