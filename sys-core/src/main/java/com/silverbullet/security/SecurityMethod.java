package com.silverbullet.security;

/**
 * 安全加密的方法
 * Created by jeffyuan on 2018/12/4.
 */
public enum SecurityMethod {
    NULL("NULL",0), RSA("RSA",1);

    private String name;
    private int index;

    private SecurityMethod(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static SecurityMethod getByName(String name) {
        for (SecurityMethod method : SecurityMethod.values()) {
            if (method.name.equalsIgnoreCase(name)) {
                return method;
            }
        }
        return SecurityMethod.NULL;
    }
}
