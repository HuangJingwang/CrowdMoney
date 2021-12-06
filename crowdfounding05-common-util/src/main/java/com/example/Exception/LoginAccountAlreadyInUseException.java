package com.example.Exception;

/**
 * 保存或更新admin时会发生的 用户名冲突异常
 */
public class LoginAccountAlreadyInUseException extends RuntimeException{
   private static final long serialVersionUID = 1L;

    public LoginAccountAlreadyInUseException() {
    }

    public LoginAccountAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAccountAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAccountAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAccountAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
