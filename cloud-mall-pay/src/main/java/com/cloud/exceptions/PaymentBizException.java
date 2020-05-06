package com.cloud.exceptions;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;


public class PaymentBizException extends BizException {

    private static final long serialVersionUID = 4324957633191749114L;

    public PaymentBizException() {}

    public PaymentBizException(String message) {
        super(message);
        System.out.println("PaymentBizException, message: "+message);
    }

    public PaymentBizException(int code, String message) {
        super(code, message);
        System.out.println("PaymentBizException, code:"+code+", message: "+message);
    }

    public PaymentBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        System.out.println("PaymentBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public PaymentBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        System.out.println("PaymentBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
