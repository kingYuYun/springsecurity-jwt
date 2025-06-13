package com.yuy.springsecurity_jwt.entity;

import lombok.Data;

@Data
public class User {
    public Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String nickName;

}
