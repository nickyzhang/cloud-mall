package com.cloud.common.core.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author nickyzhang
 */
@Data
public class SocialToken implements Serializable {

    private static final long serialVersionUID = 1348886993831116007L;

    private String accessToken;

    private String expireIn;

    private String refreshToken;

    private String openId;

    private String scope;

    private String unionId;

    public SocialToken() {}

    public SocialToken(String accessToken, String expireIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
    }

    public SocialToken(String accessToken, String expireIn, String refreshToken, String openId) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
        this.openId = openId;
    }
}
