package com.ankoye.jelly.auth.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthToken implements Serializable{

    String accessToken;

    String refreshToken;

    // jwt短令牌
    String jti;
}