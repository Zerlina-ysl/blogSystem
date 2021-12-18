package com.company.vueblog.shiro;


import lombok.Data;

/**
 * 登录成功之后返回一个用户信息的载体
 */
@Data
public class AccountProfile {

    private Long id;
    private String username;
    private String avatar;

}
