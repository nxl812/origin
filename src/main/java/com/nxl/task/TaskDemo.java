package com.nxl.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TaskDemo {


//    @Scheduled(cron="*/5 * * * * *")
//    @Scheduled(cron="* * 11 * * *")
    public void doSomething() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime())+"do something");
    }


}
