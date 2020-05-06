package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserBizException extends BizException {

    private static final long serialVersionUID = 4324957633191749114L;

    private Logger LOGGER = LoggerFactory.getLogger(UserBizException.class);


    public UserBizException() {}

    public UserBizException(String message) {
        super(message);
        LOGGER.error("UserBizException, message: "+message);
    }

    public UserBizException(int code, String message) {
        super(code, message);
        LOGGER.error("UserBizException, code:"+code+", message: "+message);
    }

    public UserBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("UserBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public UserBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("UserBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
