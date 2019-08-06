package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;

public class UserBizException extends BizException {

    private static final long serialVersionUID = 4324957633191749114L;

    public UserBizException() {}

    public UserBizException(String message) {
        super(message);
        System.out.println("UserBizException, message: "+message);
    }

    public UserBizException(int code, String message) {
        super(code, message);
        System.out.println("UserBizException, code:"+code+", message: "+message);
    }

    public UserBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        System.out.println("UserBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public UserBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        System.out.println("UserBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
