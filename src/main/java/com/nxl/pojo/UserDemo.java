package com.nxl.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserDemo implements Serializable {
    private String id;
    private String name;
    private String password;
    private String roleSet;

    public UserDemo(String id, String name, String password, String roleSet) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roleSet = roleSet;
    }

}
