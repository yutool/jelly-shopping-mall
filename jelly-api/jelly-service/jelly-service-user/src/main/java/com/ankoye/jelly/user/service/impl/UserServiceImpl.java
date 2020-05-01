package com.ankoye.jelly.user.service.impl;

import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.mapper.UserMapper;
import com.ankoye.jelly.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User getByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>()
            .eq("username", username)
        );
    }
}
