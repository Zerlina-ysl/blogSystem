package com.company.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.vueblog.common.lang.Result;
import com.company.vueblog.entity.Blog;
import com.company.vueblog.service.BlogService;
import com.company.vueblog.util.ShiroUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小李
 * @since 2021-12-12
 */
@RestController
//@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService bservice;

    /**
     * 实现分页
     * @param currentPage
     * @return
     */
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue="1") Integer currentPage,
                       @RequestParam(value="username")String username){

        Page page  = new Page(currentPage,2);
        IPage<Blog> pageData = bservice.page(page, new QueryWrapper<Blog>().orderByDesc("created").eq("username",username));

        return Result.succ(pageData);
    }



    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name="id") Long id){
        Blog blog = bservice.getById(id);
        Assert.notNull("该博客不存在");

        return Result.succ(blog);

    }
//@RequestBody不支持GET请求
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result list(@Validated @RequestBody Blog blog,
                       @RequestParam(value="username")String username){
        System.out.println(blog);
        Blog temp=null;
        temp.setUsername(username);
        if(blog.getId()!=null) {
            //参数带有id 处于编辑状态
            temp=bservice.getById(blog.getId());
            //当前登录编辑博客的用户id == 博客的用户id
            Assert.isTrue(temp.getUserId().longValue()==ShiroUtils.getProfile().getId().longValue(),"没有权限编辑");


        }else{
            //处于添加状态
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setStatus(0);
            temp.setCreated(LocalDateTime.now());

        }
    //功能完成后需要向前端返回edit后的数据
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        bservice.saveOrUpdate(temp);

        return Result.succ("操作成功");

    }



    //@PathVariable动态路由
    @RequiresAuthentication  //需要认证之后才能操作
    @PostMapping("/blogdel/{id}")
    public Result del(@PathVariable Long id){
        boolean b = bservice.removeById(id);
        //判断是否为空 为空则断言异常
        if(b==true){

            return Result.succ("文章删除成功");
        }else{
            return Result.fail("文章删除失败");
        }
    }

}
