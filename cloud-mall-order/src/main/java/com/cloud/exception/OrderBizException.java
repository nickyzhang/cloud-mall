package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderBizException extends BizException {

    private static final long serialVersionUID = 5513996208206999028L;

    private Logger LOGGER = LoggerFactory.getLogger(OrderBizException.class);

    public OrderBizException() {}

    public OrderBizException(String message) {
        super(message);
        LOGGER.error("OrderBizException, message: "+message);
    }

    public OrderBizException(int code, String message) {
        super(code, message);
        LOGGER.error("OrderBizException, code:"+code+", message: "+message);
    }

    public OrderBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("OrderBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public OrderBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("OrderBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
