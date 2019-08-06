package com.cloud.common.core.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author nickyzhang
 */
@Data
public class SocialUserInfo implements Serializable {

    private static final long serialVersionUID = 6208385779710547965L;

    private String nickname;

    private String gender;

    private String avator;

    private int ret;

    private String msg;

    public SocialUserInfo() {}

    public SocialUserInfo(String nickname, String gender, String avator) {
        this.nickname = nickname;
        this.gender = gender;
        this.avator = avator;
    }
}
