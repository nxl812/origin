package com.nxl.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RoleDemo {

    private String id;
    private String role;
    private Set<PermissionDemo> permissionsSet;


    public RoleDemo(String id, String role, Set<PermissionDemo> permissionsSet) {
        this.id = id;
        this.role = role;
        this.permissionsSet = permissionsSet;
    }
}
