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
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = -4762928619495260423L;

    private Integer code;

    private String message;

    private T data;

    public RestResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult<T> success(T sku) {
        RestResult<T> result = new RestResult<>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(sku);
        return result;
    }

    public static RestResult error(ResultCode resultCode) {
        RestResult result = new RestResult();
        result.setResultCode(resultCode);
        return result;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

}
