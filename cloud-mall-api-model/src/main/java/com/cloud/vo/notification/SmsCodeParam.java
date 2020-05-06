package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogSmsCodeParam",description = "/catalog发送短信验证码参数")
public class SmsCodeParam implements Serializable {

    private static final long serialVersionUID = 1549337711687141357L;

    @ApiModelProperty(value = "/catalog手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "/catalog验证码", required = true)
    private String code;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;

}
