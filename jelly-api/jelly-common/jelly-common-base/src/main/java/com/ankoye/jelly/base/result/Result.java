package com.ankoye.jelly.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = -4762928619495260423L;

    private Integer code;

    private String message;

    private Object data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result error(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public Map<String, Object> simple() {
        Map<String, Object> simple = new HashMap<>();
        this.data = simple;
        return simple;
    }

}
