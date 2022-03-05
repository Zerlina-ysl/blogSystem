package com.company.vueblog.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.company.vueblog.entity.User;
import com.company.vueblog.service.UserService;
import com.company.vueblog.util.JwtUtils;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录逻辑的处理
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    //filter的login()操作会到达该方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;
//        DebugLogger log = null;
        log.info("jwt----------------->{}", jwt);
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        User user = userService.getById(Long.parseLong(userId));
        if(user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定！");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());
        /**
         *  public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) {
         *         this.principals = new SimplePrincipalCollection(principal, realmName);
         *         this.credentials = credentials;
         *     }
         */
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
