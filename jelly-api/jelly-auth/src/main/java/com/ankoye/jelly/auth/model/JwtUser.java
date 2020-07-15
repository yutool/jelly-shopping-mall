package com.ankoye.jelly.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author ankoye@qq.com
 */
public class JwtUser extends User {

    @Getter
    @Setter
    private String userId;

    public JwtUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities);
        this.userId = userId;
    }

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
