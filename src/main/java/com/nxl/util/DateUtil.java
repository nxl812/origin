package com.nxl.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Integer getWeekDay(Date date) {
        Integer ret = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            ret=7;
        } else if (weekday == 2) {
            ret=1;
        } else if (weekday == 3) {
            ret=2;
        } else if (weekday == 4) {
            ret=3;
        } else if (weekday == 5) {
            ret=4;
        } else if (weekday == 6) {
            ret=5;
        } else if (weekday == 7) {
            ret=6;
        }
        return ret;
    }
}
