package com.ankoye.jelly.oauth;

import com.ankoye.jelly.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security需要我们实现几个东西，第一个是UserDetails：
 * 这个接口中规定了用户的几个必须要有的方法，所以我们创建一个JwtUser类来实现这个接口。
 * 为什么不直接使用User类？因为这个UserDetails完全是为了安全服务的，
 * 它和我们的领域类可能有部分属性重叠，但很多的接口其实是安全定制的，所以最好新建一个类
 *
 * @version   1.0.0
 */
public class JwtUserDetails implements UserDetails {
    private String userId;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /** 账号是否过期 */
    private final Boolean accountNonExpired;

    /** 账户是否锁定 */
    private final Boolean accountNonLocked;

    /** 密码是否过期 */
    private final Boolean credentialsNonExpired;

    /** 是否激活 */
    private final Boolean enabled;

    public JwtUserDetails(User user) {
        this(user, true, true, true, true, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }

    // 看怎么设计的，如果一个用户有多个角色，需要在UserDetailServiceImpl查询多个角色
    // List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
    // grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_xxx"));
    public JwtUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this(user, true, true, true, true, authorities);
    }

    public JwtUserDetails(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (user != null) {
            this.userId = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = authorities;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public String getUserId() {
        return userId;
    }

    // 获取权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    // 账号是否未过期，默认是false，记得要改一下
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否未锁定，默认是false，记得也要改一下
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 账号凭证是否未过期，默认是false，记得还要改一下
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 这个有点抽象不会翻译，默认也是false，记得改一下
    @Override
    public boolean isEnabled() {
        return true;
    }
}