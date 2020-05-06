package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogMailCodeParam",description = "/catalog发送邮件验证码参数")
public class MailCodeParam implements Serializable {

    private static final long serialVersionUID = 1549337711687141357L;

    @ApiModelProperty(value = "/catalog邮件地址", required = true)
    private String email;

    @ApiModelProperty(value = "/catalog邮件主题", required = true)
    private String subject = "/catalogCloudMall邮箱验证提醒";

    @ApiModelProperty(value = "/catalog邮件验证码", required = true)
    private String code;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog是否已HTML格式发送")
    private boolean html = false;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;

}
