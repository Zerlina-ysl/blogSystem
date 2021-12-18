package com.company.vueblog.service.impl;

import com.company.vueblog.entity.User;
import com.company.vueblog.mapper.UserMapper;
import com.company.vueblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
