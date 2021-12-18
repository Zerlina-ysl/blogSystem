package com.company.vueblog.controller;


import com.company.vueblog.common.lang.Result;
import com.company.vueblog.entity.User;
import com.company.vueblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小李
 * @since 2021-12-12
 */
@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService uservice ;

    //开启登录后访问的权限注解
    @RequiresAuthentication
    @GetMapping("/test")
    public Object Test(){
        User user=  uservice.getById(1L);
        return Result.succ(user);

    }

    //@RequestBody 注解主要接收前端传给后端的json字符串
    //@Validated 用于检查User中使用的规则 不正确则抛出异常
    @PostMapping("/save")
    //开启效验注解
    public Result save(@Validated @RequestBody User user){
        return Result.succ(user);

    }



}
