package com.cloud.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "/catalogSocialBindParam", description = "/catalog社交用户绑定参数")
public class SocialBindParam  implements Serializable{

    private static final long serialVersionUID = -6798545884037215276L;

    @ApiModelProperty(value = "/catalog开放账号ID",required = true)
    private String openId;

    @ApiModelProperty(value = "/catalog绑定的手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "/catalog获取用户详细信息的URL",required = true)
    private String userInfoUrl;
}
