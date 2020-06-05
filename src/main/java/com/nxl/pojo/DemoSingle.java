package com.nxl.pojo;

public enum DemoSingle {
    SINGLE;
    private UserDemo userDemo = null;

    private DemoSingle() {
        userDemo = new UserDemo();
        userDemo.setName("naixianlei");
    }

    public DemoSingle getInstance(){
        return SINGLE;
    }

    public UserDemo getUserDemo() {
        return userDemo;
    }
}
