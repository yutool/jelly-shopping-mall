package com.ankoye.jelly.base.result;

/**
 * @author ankoye@qq.com
 */
public class Wrappers {

    /**
     * Instantiates a new wrap mapper.
     */
    private Wrappers() {
    }

    public static <E> Wrapper<E> success() {
        return new Wrapper<>(ResultCode.SUCCESS);
    }

    public static <E> Wrapper<E> success(E data) {
        return new Wrapper<>(data);
    }

    public static <E> Wrapper<E> error(ResultCode resultCode) {
        return new Wrapper<>(resultCode);
    }

    public static <E> Wrapper<E> error(Integer code, String message) {
        return new Wrapper<>(code, message);
    }

}
