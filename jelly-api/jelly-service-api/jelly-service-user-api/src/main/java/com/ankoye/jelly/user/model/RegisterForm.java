package com.ankoye.jelly.user.model;

import com.ankoye.jelly.user.domain.User;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ankoye@qq.com
 */
@Data
public class RegisterForm {

    private String username;

    private String email;

    private String password;

    private String verifyCode;

    public User convertToUser() {
        UserConvert userConvert = new UserConvert();
        return userConvert.convert(this);
    }

    private static class UserConvert extends Converter<RegisterForm, User> {
        @Override
        protected User doForward(RegisterForm registerForm) {
            User user = new User();
            BeanUtils.copyProperties(registerForm, user);
            return user;
        }

        @Override
        protected RegisterForm doBackward(User user) {
            return null;
        }
    }
}
