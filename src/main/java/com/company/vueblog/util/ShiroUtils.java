package com.company.vueblog.util;

import com.company.vueblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.parser.Authorization;

public class ShiroUtils {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
