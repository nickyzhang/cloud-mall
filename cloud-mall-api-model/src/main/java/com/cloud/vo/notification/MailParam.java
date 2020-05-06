package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogMailParam", description = "/catalog发送邮件参数")
public class MailParam implements Serializable {

    private static final long serialVersionUID = 3996473962473945888L;

    @ApiModelProperty(value = "/catalog邮件地址", required = true)
    private String email;

    @ApiModelProperty(value = "/catalog邮件主题", required = true)
    private String subject;

    @ApiModelProperty(value = "/catalog邮件内容", required = true)
    private String content;

    @ApiModelProperty(value = "/catalog是否已HTML格式发送")
    private boolean html = false;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;
}
