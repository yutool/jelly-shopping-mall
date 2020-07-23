package com.ankoye.jelly.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Wrapper<T> implements Serializable {

    private static final long serialVersionUID = -7827022628358160949L;

    private Integer code;

    private String message;

    private T data;

    public Wrapper(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Wrapper(ResultCode resultCode) {
        setResultCode(resultCode);
    }

    public Wrapper(T data) {
        setResultCode(ResultCode.SUCCESS);
        this.data = data;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

}
