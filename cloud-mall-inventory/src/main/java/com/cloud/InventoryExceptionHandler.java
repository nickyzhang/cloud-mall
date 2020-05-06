package com.cloud;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.GlobalExceptionHandler;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class InventoryExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler({ FeignException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult feignException(FeignException e) {
        log.error("Feign异常",e.getCause().getMessage());
        ResponseResult response = new ResponseResult(ResultCodeEnum.NOT_FOUND.getCode(),e.getMessage());
        return response;
    }

    @ExceptionHandler({ HystrixRuntimeException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult processException(HystrixRuntimeException e) {
        log.error("Hystrix运行时异常",e.getCause().getMessage());
        ResponseResult response = new ResponseResult(ResultCodeEnum.NOT_FOUND.getCode(), e.getMessage());
        return response;
    }
}
