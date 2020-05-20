package com.nxl.pojo.mybatis;

import java.util.Date;

public class HolidayDetail {
    private Integer id;

    private String startDay;

    private Integer dayRange;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private Integer isDisable;

    public HolidayDetail(Integer id, String startDay, Integer dayRange, Integer type, Date createTime, Date updateTime, Integer isDisable) {
        this.id = id;
        this.startDay = startDay;
        this.dayRange = dayRange;
        this.type = type;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDisable = isDisable;
    }

    public HolidayDetail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay == null ? null : startDay.trim();
    }

    public Integer getDayRange() {
        return dayRange;
    }

    public void setDayRange(Integer dayRange) {
        this.dayRange = dayRange;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }
}