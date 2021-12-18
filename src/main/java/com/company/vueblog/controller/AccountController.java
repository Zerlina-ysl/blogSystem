package com.company.vueblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.vueblog.common.dto.LoginDto;
import com.company.vueblog.common.lang.Result;
import com.company.vueblog.entity.User;
import com.company.vueblog.service.UserService;
import com.company.vueblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserService uservice;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping("login")
    public Result login(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {
        //密码的匹配 根据密码获取用户
        User user = uservice.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //通过全局异常捕捉断言异常
        Assert.notNull(user, "用户不存在");

//需要数据库也进行md5加密才可
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");


        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );

    }
        @RequiresAuthentication
        @GetMapping("/logout")
         public Result logout(){

            SecurityUtils.getSubject().logout();

            return Result.succ("注销成功");
        }

}
