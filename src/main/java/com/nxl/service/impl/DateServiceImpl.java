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
import java.util.regex.Pattern;

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

//        Map<Long,Integer> holidayMapBk = new HashMap<>();//下面holidayMap替换为holidayMapBk,感觉目前没啥必要=以后数据量大了可以启用
//        final Long START_TIME_FINAL= startTime;
//        holidayMap.keySet().stream().filter(e-> e.compareTo(START_TIME_FINAL)>=0).forEach(e-> holidayMapBk.put(e,holidayMapBk.get(e)));

        for (int i = 0; i <= add; i++) {//先判断这天到底是个啥
            if (i>0){
                startTime+=ONE_DAY_TIMESTAMP;
            }

//            if ( holidayMap.containsKey(startTime)&&holidayMap.get(startTime).equals(Constant.HolidayDetail.MORE_WORD) ){//不需要特殊管理调休日
//                continue;
//            }

            Integer type;
            while (  (null!=(type = holidayMap.get(startTime)) && type.equals(Constant.HolidayDetail.HOLIDAY))
                    ||
                    (  (null!=type && !type.equals(Constant.HolidayDetail.MORE_WORD))  && isWeekend(startTime) )
            ){
                startTime+=ONE_DAY_TIMESTAMP;
            }

        }
//        判断是不是超出最大时间，超出最大时间不太可信
//        if (startTime>maxTimeStamp){
//            String format = String.format("addWorkDay result over maxTimeStamp,startTime:%s", startTime.toString());
//            log.error(format);
//            throw new GlobalException(500,format);
//        }
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
        if (Constant.yyyyMMdd.equals(pattern)){
            boolean matches = Pattern.matches("^((?!0000)[0-9]{4}((0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])(29|30)|(0[13578]|1[02])31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)0229)$", day);
            if (!matches){
                String format = String.format("addWorkDay request param day error,day:%s", day);
                log.error(format);
                throw new GlobalException(500,format);
            }
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
        boolean matches = Pattern.matches("^((?!0000)[0-9]{4}((0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])(29|30)|(0[13578]|1[02])31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)0229)$", "20200229");
        System.out.println(matches);
    }
}
