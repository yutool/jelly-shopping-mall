package com.ankoye.jelly.oauth;

import com.ankoye.jelly.dao.UserMapper;
import com.ankoye.jelly.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws  UsernameNotFoundException {
        // 从数据库中获取用户，如果需要的话，再获取权限
         User user = userMapper.selectOne(new QueryWrapper<User>()
                 .eq("email", account)
         );
        return new JwtUserDetails(user);
    }
}

