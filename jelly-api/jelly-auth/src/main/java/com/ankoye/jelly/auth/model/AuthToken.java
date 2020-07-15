package com.ankoye.jelly.auth.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 */
@Data
public class AuthToken implements Serializable{

    String accessToken;

    String refreshToken;

    /** jwt短令牌 */
    String jti;
}