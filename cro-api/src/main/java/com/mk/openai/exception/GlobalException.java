package com.mk.openai.exception;


import com.mk.openai.enums.ErrorCodeEnum;

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -1700234834583632562L;
    private int code;

    public GlobalException(String message) {
        super(message);
        this.code = ErrorCodeEnum.INVALID_ARGUMENT.getCode();
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }

    public GlobalException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public GlobalException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.code = ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
    }

    protected GlobalException(
            String message,
            int code,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GlobalException)) {
            return false;
        } else {
            GlobalException other = (GlobalException) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                return this.getCode() == other.getCode();
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GlobalException;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = result * 59 + this.getCode();
        return result;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String toString() {
        return "GlobalException(code=" + this.getCode() + ")";
    }
}
