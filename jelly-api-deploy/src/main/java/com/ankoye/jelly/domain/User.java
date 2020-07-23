package com.ankoye.jelly.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
    private String id;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private String password;

    private String avatar;

    private String gender;

    private Date birthday;

    private Date createTime;

    private Date updateTime;

    private Date lastLoginTime;

    private Integer userLevel;

    private Integer points;

    private String role;

    private Integer status;

}