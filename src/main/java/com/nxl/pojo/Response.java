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
public class Response<T> implements Serializable {
    private String msg;
    private Integer code;
    private Boolean success;
    private T t;


}


