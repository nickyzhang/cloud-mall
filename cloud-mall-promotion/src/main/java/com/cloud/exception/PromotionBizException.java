package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PromotionBizException extends BizException {

    private static final long serialVersionUID = 66080881304949466L;

    private Logger LOGGER = LoggerFactory.getLogger(PromotionBizException.class);


    public PromotionBizException() {}

    public PromotionBizException(String message) {
        super(message);
        LOGGER.error("PromotionBizException, message: "+message);
    }

    public PromotionBizException(int code, String message) {
        super(code, message);
        LOGGER.error("PromotionBizException, code:"+code+", message: "+message);
    }

    public PromotionBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("PromotionBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public PromotionBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("PromotionBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
