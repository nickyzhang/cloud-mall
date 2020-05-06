package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CouponBizException extends BizException {

    private static final long serialVersionUID = 5513996208206999028L;
    
    private Logger LOGGER = LoggerFactory.getLogger(CouponBizException.class);

    public CouponBizException() {}

    public CouponBizException(String message) {
        super(message);
        LOGGER.error("CouponBizException, message: "+message);
    }

    public CouponBizException(int code, String message) {
        super(code, message);
        LOGGER.error("CouponBizException, code:"+code+", message: "+message);
    }

    public CouponBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("CouponBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public CouponBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("CouponBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
