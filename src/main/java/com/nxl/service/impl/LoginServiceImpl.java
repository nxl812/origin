package com.nxl.service.impl;

import com.nxl.pojo.PermissionDemo;
import com.nxl.pojo.RoleDemo;
import com.nxl.pojo.UserDemo;
import com.nxl.service.LoginServiceI;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoginServiceImpl implements LoginServiceI {
    //https://www.jianshu.com/p/7f724bec3dc3
    @Override
    public UserDemo getUserByName(String name) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(name);
    }

    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private UserDemo getMapByName(String userName){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        PermissionDemo permissions1 = new PermissionDemo("1","query");
        PermissionDemo permissions2 = new PermissionDemo("2","add");
        Set<PermissionDemo> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        RoleDemo role = new RoleDemo("1","admin",permissionsSet);
        Set<RoleDemo> roleSet = new HashSet<>();
        roleSet.add(role);
        UserDemo user = new UserDemo("1","wsl","123456",roleSet);
        Map<String ,UserDemo> map = new HashMap<>();
        map.put(user.getUserName(), user);

        Set<PermissionDemo> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        RoleDemo role1 = new RoleDemo("2","user",permissionsSet1);
        Set<RoleDemo> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        UserDemo user1 = new UserDemo("2","zhangsan","123456",roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }
}
