package com.ankoye.jelly.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ankoye@qq.com
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Wrapper<T> implements Serializable {

    private static final long serialVersionUID = -7827022628358160949L;

    /** 成功码 */
    public static final Integer SUCCESS_CODE = ResultCode.SUCCESS.code();

    /** 成功信息 */
    public static final String SUCCESS_MESSAGE = "操作成功";

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

    public boolean isSuccess() {
        if (!Objects.equals(this.code, SUCCESS_CODE)) {
            return false;
        }
        if (this.data instanceof Integer) {
            return (Integer) this.data > 0;
        }
        if (this.data instanceof Boolean) {
            return (Boolean) this.data;
        }
        return this.data != null;
    }
}
