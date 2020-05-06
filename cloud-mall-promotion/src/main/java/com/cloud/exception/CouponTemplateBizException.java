package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CouponTemplateBizException extends BizException {

    private static final long serialVersionUID = 7493319146598869290L;

    private Logger LOGGER = LoggerFactory.getLogger(CouponTemplateBizException.class);


    public CouponTemplateBizException() {}

    public CouponTemplateBizException(String message) {
        super(message);
        LOGGER.error("CouponTemplateBizException, message: "+message);
    }

    public CouponTemplateBizException(int code, String message) {
        super(code, message);
        LOGGER.error("CouponTemplateBizException, code:"+code+", message: "+message);
    }

    public CouponTemplateBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("CouponTemplateBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public CouponTemplateBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("CouponTemplateBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
