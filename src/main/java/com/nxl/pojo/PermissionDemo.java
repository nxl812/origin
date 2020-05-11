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
public class PermissionDemo implements Serializable {
    private String id;
    private String permission;


    public PermissionDemo(String id, String permission) {
        this.id = id;
        this.permission = permission;
    }
}
