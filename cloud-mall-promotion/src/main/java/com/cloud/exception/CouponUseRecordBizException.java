package com.cloud.exception;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CouponUseRecordBizException extends BizException {
    
    private static final long serialVersionUID = 2930561843347549540L;
    
    private Logger LOGGER = LoggerFactory.getLogger(CouponUseRecordBizException.class);
    
    public CouponUseRecordBizException() {}

    public CouponUseRecordBizException(String message) {
        super(message);
        LOGGER.error("CouponUseRecordBizException, message: "+message);
    }

    public CouponUseRecordBizException(int code, String message) {
        super(code, message);
        LOGGER.error("CouponUseRecordBizException, code:"+code+", message: "+message);
    }

    public CouponUseRecordBizException(ResultCodeEnum codeEnum) {
        super(codeEnum);
        LOGGER.error("CouponUseRecordBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }

    public CouponUseRecordBizException(ResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        LOGGER.error("CouponUseRecordBizException, code:"+codeEnum.getCode()+", message: "+codeEnum.getMessage());
    }
}
