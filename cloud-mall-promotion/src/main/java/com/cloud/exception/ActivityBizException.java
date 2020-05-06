package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityBizException extends BizException {
    
    private static final long serialVersionUID = 2930561843347549540L;
    
    private Logger LOGGER = LoggerFactory.getLogger(ActivityBizException.class);
    
    public ActivityBizException() {}

    public ActivityBizException(String message) {
        super(message);
        LOGGER.error("ActivityBizException, message: "+message);
    }

    public ActivityBizException(int code, String message) {
        super(code, message);
        LOGGER.error("ActivityBizException, code:"+code+", message: "+message);
    }

    public ActivityBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("ActivityBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public ActivityBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("ActivityBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
