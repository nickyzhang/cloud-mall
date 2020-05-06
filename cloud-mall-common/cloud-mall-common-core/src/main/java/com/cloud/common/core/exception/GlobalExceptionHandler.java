package com.cloud.common.core.exception;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数非法异常
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常: {}",e.getMessage(),e);
        ResponseResult response = new ResponseResult(ResultCodeEnum.ILLEGAL_ARGUMENT_EXCEPTION.getCode(),e.getMessage());
        return response;
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult businessException(BizException e) {
        log.error("业务异常: {}",e.getMessage(),e);
        ResponseResult response = new ResponseResult(e.getCode() == 0 ? ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode() : e.getCode(),e.getMessage());
        return response;
    }

    /**
     * 全局信息异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        log.info("全局异常信息: {}", e.getMessage(), e);
        ResponseResult response = new ResponseResult(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
        return response;
    }
}
