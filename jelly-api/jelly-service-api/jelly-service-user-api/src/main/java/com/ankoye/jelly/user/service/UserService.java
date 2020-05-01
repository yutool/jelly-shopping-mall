package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.User;

public interface UserService {
    User getByUsername(String username);
}
