package com.nxl.service.impl;

import com.nxl.config.Constant;
import com.nxl.dao.mybatis.HolidayDetailMapper;
import com.nxl.pojo.GlobalException;
import com.nxl.pojo.mybatis.HolidayDetail;
import com.nxl.pojo.mybatis.HolidayDetailExample;
import com.nxl.service.DateServiceI;
import com.nxl.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Getter
@Setter
public class DateServiceImpl implements DateServiceI, CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DateServiceImpl.class);

    private Map<Long,Integer> holidayMap;
    private Long maxTimeStamp;

    private static final Long ONE_DAY_TIMESTAMP=86400000L;//24*60*60*1000

    @Resource
    private HolidayDetailMapper holidayDetailMapper;

    @Override
    public void flushHoliday() {
        log.info("flushHoliday start");
        holidayMap = new HashMap<>();

        HolidayDetailExample example = new HolidayDetailExample();
        example.createCriteria().andIsDisableEqualTo(Constant.DB_IS_ABLE);
        List<HolidayDetail> holidayDetails = holidayDetailMapper.selectByExample(example);

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.yyyyMMdd);

        for (HolidayDetail e : holidayDetails) {
            Date parse = null;
            try {
                parse = sdf.parse(e.getStartDay());
            } catch (ParseException ex) {
                log.error("flushHoliday parse date error,dateStr is {}", e.getStartDay());
            }

            Integer type = e.getType();
            for (int i = 0; i < e.getDayRange(); i++) {
                holidayMap.put(parse.getTime()+(i*ONE_DAY_TIMESTAMP),type);
            }
        }
        for (Map.Entry<Long,Integer> e:holidayMap.entrySet()) {
//            log.info("holidayMap-----key:{},value:{}",e.getKey(),e.getValue());
            log.info("holidayMap-----key:{},value:{},dayStr:{}",e.getKey(),e.getValue(),sdf.format(new Date(e.getKey())));
        }

        maxTimeStamp = holidayMap.keySet().stream().max(Comparator.naturalOrder()).get();
        log.info("maxTimeStamp:{}",maxTimeStamp);

        log.info("flushHoliday end");
    }

    @Override
    public String addWorkDay(String day, String pattern, Integer add) throws GlobalException {
        Date parse = checkRequestParam(day,pattern,add);

        Long startTime = parse.getTime();
        for (int i = 0; i <= add; i++) {//先判断这天到底是个啥
            if (i>0){
                startTime+=ONE_DAY_TIMESTAMP;
            }

            if ( holidayMap.containsKey(startTime)&&holidayMap.get(startTime).equals(Constant.HolidayDetail.MORE_WORD) ){
                continue;
            }

            while (  (holidayMap.containsKey(startTime) && holidayMap.get(startTime).equals(Constant.HolidayDetail.HOLIDAY))
                        ||
                        (  (holidayMap.containsKey(startTime) && !holidayMap.get(startTime).equals(Constant.HolidayDetail.MORE_WORD))  && isWeekend(startTime) )
                  ){
                startTime+=ONE_DAY_TIMESTAMP;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.yyyyMMdd);
        String format = sdf.format(new Date(startTime));
        return format;
    }

    private boolean isWeekend(Long startTime) {
        Date date = new Date(startTime);
        Integer weekDay = DateUtil.getWeekDay(date);
        return weekDay == 6 || weekDay == 7;
    }

    private Date checkRequestParam(String day, String pattern, Integer add) throws GlobalException {
        if (add<=0){
            String format = String.format("addWorkDay request param add error,add:%s", add.toString());
            log.error(format);
            throw new GlobalException(500,format);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parse = null;
        try {
            parse = sdf.parse(day);
        } catch (ParseException e) {
            String format = String.format("addWorkDay parse day error,dayStr:%s,pattern:%s", day, pattern);
            log.error(format);
            throw new GlobalException(500,format);
        }

        return parse;
    }

    @Override
    public void run(String... args) {
        flushHoliday();
    }


    public static void main(String[] args) {
        Date date = new Date(1593705600000L);
        System.out.println(date);
    }
}