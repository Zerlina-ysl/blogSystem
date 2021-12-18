package com.company.vueblog.service.impl;

import com.company.vueblog.entity.Blog;
import com.company.vueblog.mapper.BlogMapper;
import com.company.vueblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小李
 * @since 2021-12-12
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
