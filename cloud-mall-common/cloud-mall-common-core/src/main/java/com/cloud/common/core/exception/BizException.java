package com.cloud.common.core.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -6691127467610988589L;

    private int code;

    public BizException() {}

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(int code, Exception e) {
        super(e);
        this.code = code;
    }

    public BizException(int code, String message, Exception e) {
        super(message, e);
        this.code = code;
    }

    public BizException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

    public BizException(ResultCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }
}
