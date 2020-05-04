package com.ankoye.jelly.web.exception;

public class ServiceException extends RuntimeException {
    private Integer code;

    private String message;

    public ServiceException (String message) {
        this(1, message);
    }

    public ServiceException (Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
