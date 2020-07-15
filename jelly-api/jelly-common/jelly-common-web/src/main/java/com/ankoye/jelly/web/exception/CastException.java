package com.ankoye.jelly.web.exception;

import com.ankoye.jelly.base.result.ResultCode;

/**
 * 异常抛出类
 *
 * @author ankoye@qq.com
 */
public class CastException {
    public static void cast(Integer code, String message) {
        throw new ServiceException(code, message);
    }

    public static void cast(String message) {
        throw new ServiceException(message);
    }

    public static void cast(ResultCode resultCode) {
        throw new ServiceException(resultCode.code(), resultCode.message());
    }
}
