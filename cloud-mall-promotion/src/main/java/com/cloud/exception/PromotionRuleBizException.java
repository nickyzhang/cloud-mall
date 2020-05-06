package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PromotionRuleBizException extends BizException {

    private static final long serialVersionUID = 66080881304949466L;

    private Logger LOGGER = LoggerFactory.getLogger(PromotionRuleBizException.class);


    public PromotionRuleBizException() {}

    public PromotionRuleBizException(String message) {
        super(message);
        LOGGER.error("PromotionRuleBizException, message: "+message);
    }

    public PromotionRuleBizException(int code, String message) {
        super(code, message);
        LOGGER.error("PromotionRuleBizException, code:"+code+", message: "+message);
    }

    public PromotionRuleBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("PromotionRuleBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public PromotionRuleBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("PromotionRuleBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
