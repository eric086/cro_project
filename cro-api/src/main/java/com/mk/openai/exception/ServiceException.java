package com.mk.openai.exception;


import com.mk.openai.enums.ErrorCodeEnum;

public class ServiceException extends GlobalException {
    private static final long serialVersionUID = 949027770048757640L;

    public ServiceException(String message) {
        super(message,1000);
    }

    public ServiceException(String message, int code) {
        super(message, code);
    }

    public ServiceException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(
            String message,
            int code,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(ErrorCodeEnum enosStatus) {
        super(enosStatus.getMsg(), enosStatus.getCode());
    }

    public ServiceException(ErrorCodeEnum enosStatus, String message) {
        super(message, enosStatus.getCode());
    }
}
