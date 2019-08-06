package com.cloud.common.core.bean;

import lombok.Data;

@Data
public class WechatUserInfo extends SocialUserInfo {

    private static final long serialVersionUID = -4340095682420625914L;

    private String country;

    private String province;

    private String city;

    private String openid;

    private String unionid;

    private String privileges;

    public WechatUserInfo() {}

    public WechatUserInfo(String nickname, String gender, String avator) {
        super(nickname, gender, avator);
    }
}
