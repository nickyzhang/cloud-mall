package com.cloud.common.core.bean;

import lombok.Data;

@Data
public class QQUserInfo extends SocialUserInfo {

    private static final long serialVersionUID = 3837657853392884383L;

    private boolean vip;

    private int level;

    private boolean yellowYearVip;

    private String figureurl_1;

    private String figureurl_2;

    public QQUserInfo() {}

    public QQUserInfo(String nickname, String gender, String avator) {
        super(nickname, gender, avator);
    }
}
