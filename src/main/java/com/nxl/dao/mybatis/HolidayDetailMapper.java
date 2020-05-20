package com.nxl.dao.mybatis;

import com.nxl.pojo.mybatis.HolidayDetail;
import com.nxl.pojo.mybatis.HolidayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HolidayDetailMapper {
    int countByExample(HolidayDetailExample example);

    int deleteByExample(HolidayDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HolidayDetail record);

    int insertSelective(HolidayDetail record);

    List<HolidayDetail> selectByExample(HolidayDetailExample example);

    HolidayDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HolidayDetail record, @Param("example") HolidayDetailExample example);

    int updateByExample(@Param("record") HolidayDetail record, @Param("example") HolidayDetailExample example);

    int updateByPrimaryKeySelective(HolidayDetail record);

    int updateByPrimaryKey(HolidayDetail record);
}