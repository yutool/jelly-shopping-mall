package com.ankoye.jelly.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4762928619495260423L;

    private Integer code;

    private String message;

    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public Result<T> error(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static Result<Object> success() {
        Result<Object> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result<Object> error(ResultCode resultCode) {
        Result<Object> result = new Result<>();
        result.setResultCode(resultCode);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

}
