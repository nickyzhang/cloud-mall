package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogRemoveMailCodeParam", description = "/catalog删除邮件验证码参数")
public class RemoveMailCodeParam implements Serializable {

    private static final long serialVersionUID = -8965619753263094861L;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog手机号码", required = true)
    private String email;
}
