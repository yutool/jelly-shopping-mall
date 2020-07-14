package com.ankoye.jelly.user.service.rpc;

import com.ankoye.jelly.base.constant.Role;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.mapper.UserMapper;
import com.ankoye.jelly.user.service.UserService;
import com.ankoye.jelly.util.IdUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByAccount(String account) {
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("email", account)
                .or().eq("phone", account)
        );
    }

    @Override
    public int add(User user) {
        Date date = new Date();
        user.setId(IdUtils.getUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setLastLoginTime(date);
        user.setRole(Role.ROLE_USER);
        return userMapper.insert(user);
    }
}
