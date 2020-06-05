package com.ankoye.jelly.auth.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginForm {
    @NotNull
    private String account;

    @NotNull
    @Length(min = 6, max = 16)
    private String password;
}
