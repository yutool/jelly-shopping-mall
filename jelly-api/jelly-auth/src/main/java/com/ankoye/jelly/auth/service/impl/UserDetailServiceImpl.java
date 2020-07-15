package com.ankoye.jelly.auth.service.impl;

import com.ankoye.jelly.auth.model.JwtUser;
import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 客户端信息认证
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 密码登录这里不为空
//        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
//        if(authentication == null){
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId("jelly-serve");
//            System.out.println(username + " " + clientDetails);
//            if(clientDetails != null){
//                // 秘钥
//                String clientSecret = clientDetails.getClientSecret();
//
//                return new org.springframework.security.core.userdetails.User(
//                        username, clientSecret, clientDetails.getAuthorities());
//            }
//        }

        // 用户信息认证
        Wrapper<User> wrapper = userFeign.findByAccount(account);
        User user = wrapper.getData();
        return user == null
                ? null
                : new JwtUser(user.getId(), account, user.getPassword(),
                                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }

}
