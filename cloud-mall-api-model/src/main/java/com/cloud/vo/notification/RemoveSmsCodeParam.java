package com.cloud.vo.notification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogRemoveSmsCodeParam", description = "/catalog删除短信验证码参数")
public class RemoveSmsCodeParam implements Serializable {

    private static final long serialVersionUID = -6675644848994241055L;

    @ApiModelProperty(value = "/catalog发送源", required = true)
    private String source;

    @ApiModelProperty(value = "/catalog手机号码", required = true)
    private String mobile;
}
