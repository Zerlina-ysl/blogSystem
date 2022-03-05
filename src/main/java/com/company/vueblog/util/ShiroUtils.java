package com.company.vueblog.util;

import com.company.vueblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.parser.Authorization;

/**
 * 通过shiro获得当前登录用户
 */
public class ShiroUtils {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
