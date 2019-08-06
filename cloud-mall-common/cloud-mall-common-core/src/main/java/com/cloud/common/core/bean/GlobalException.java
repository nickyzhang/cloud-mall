package com.cloud.common.core.bean;


import com.cloud.common.core.constants.SizeConstants;
import com.cloud.common.core.constants.SymbolConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@Slf4j
public class GlobalException {
    /** 运行环境 */
    private String environment;
    /** 应用名字 */
    private String application;
    /** 异常信息 */
    private String message;
    /** 异常根源 */
    private String cause;
    /** 异常类型 */
    private String simpleName;
    /** 异常堆栈信息 */
    private String stackTrace;
    /**
     * 创建时间
     * DateTimeFormat：入参格式化，将传入的字符串正确转化为时间类型，即传入2018-08-02 22:05:55，不会抛出异常
     * JsonFormat: 出参格式化
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public GlobalException getGlobalException(Exception e, String environment, String application) {
        this.environment = environment;
        String message = e.getMessage();
        if (StringUtils.isNotBlank(message) && message.length() > SizeConstants.EXCEPTION_MESSAGE_MAX_LENGTH) {
            this.message = StringUtils.substring(message,0, SizeConstants.EXCEPTION_MESSAGE_MAX_LENGTH);
            this.message.concat(SymbolConstants.APOSTROPHE_SYMBOL);
        }
        this.simpleName = e.getClass().getSimpleName();
        String cause = e.getCause() != null ? e.getCause().toString() : null;
        if (StringUtils.isNotBlank(cause) && cause.length() > SizeConstants.EXCEPTION_CAUSE_MAX_LENGTH) {
            this.cause = StringUtils.substring(cause,0, SizeConstants.EXCEPTION_CAUSE_MAX_LENGTH);
            this.cause.concat(SymbolConstants.APOSTROPHE_SYMBOL);
        }
        this.stackTrace = Arrays.toString(e.getStackTrace());
        return this;
    }
}
