package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CartBizException extends BizException {

    private static final long serialVersionUID = 5513996208206999028L;

    private Logger LOGGER = LoggerFactory.getLogger(CartBizException.class);

    public CartBizException() {}

    public CartBizException(String message) {
        super(message);
        LOGGER.error("CartBizException, message: "+message);
    }

    public CartBizException(int code, String message) {
        super(code, message);
        LOGGER.error("CartBizException, code:"+code+", message: "+message);
    }

    public CartBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("CartBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public CartBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("CartBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
