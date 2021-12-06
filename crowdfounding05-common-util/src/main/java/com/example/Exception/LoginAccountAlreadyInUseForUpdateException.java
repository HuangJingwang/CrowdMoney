package com.example.Exception;

/**
 * 保存或更新admin时会发生的 用户名冲突异常
 */
public class LoginAccountAlreadyInUseForUpdateException extends RuntimeException{
   private static final long serialVersionUID = 1L;

    public LoginAccountAlreadyInUseForUpdateException() {
    }

    public LoginAccountAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAccountAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAccountAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAccountAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
