package com.nxl.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class HolidayEntity implements Serializable {

    private Long startDayTimeStamp;
    private Integer rangeDay;
    private Integer type;

    @Override
    public String toString() {
        return "HolidayEntity{" + "startDayTimeStamp=" + startDayTimeStamp + ", rangeDay=" + rangeDay + ", type=" + type + '}';
    }
}
