package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogCheckMailCodeParam", description = "/catalog校验邮件验证码参数")
public class CheckMailCodeParam implements Serializable {

    private static final long serialVersionUID = 9193316137407165122L;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog邮件地址", required = true)
    private String email;

    @ApiModelProperty(value = "/catalog验证码", required = true)
    private String code;
}
