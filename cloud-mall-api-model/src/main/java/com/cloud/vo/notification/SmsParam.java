package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogSmsParam",description = "/catalog发送短信参数")
public class SmsParam implements Serializable {

    private static final long serialVersionUID = 1549337711687141357L;

    @ApiModelProperty(value = "/catalog手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "/catalog短信消息", required = true)
    private String message;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;

}
