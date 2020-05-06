package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "/catalogCheckSmsCodeParam", description = "/catalog校验短信验证码参数")
public class CheckSmsCodeParam implements Serializable {

    private static final long serialVersionUID = -4669895443490963781L;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "/catalog验证码", required = true)
    private String code;
}
