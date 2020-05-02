package com.ankoye.jelly.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserJwt extends User {
    private String id;          // 用户ID
    private String account;     // 账号

    public UserJwt(String id, String account, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities);
        this.id = id;
        this.account = account;
    }

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
