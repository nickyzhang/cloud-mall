package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryBizException extends BizException {

    private static final long serialVersionUID = 5589526320748535L;

    private Logger LOGGER = LoggerFactory.getLogger(InventoryBizException.class);

    public InventoryBizException(){}

    public InventoryBizException(String message) {
        super(message);
        LOGGER.error("InventoryBizException, message: "+message);
    }

    public InventoryBizException(int code, String message) {
        super(code, message);
        LOGGER.error("InventoryBizException, code:"+code+", message: "+message);
    }

    public InventoryBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("InventoryBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public InventoryBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("InventoryBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
