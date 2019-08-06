package com.cloud.common.core.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 2042461177274510504L;

    private String accessToken;

    private Long expireIn;

    private String refreshToken;

    private String openId;

    private String scope;

    private String unionId;

    public AccessToken() {
    }

    public AccessToken(String accessToken, Long expireIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
    }

    public AccessToken(String accessToken, Long expireIn, String refreshToken, String openId) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
        this.openId = openId;
    }
}
