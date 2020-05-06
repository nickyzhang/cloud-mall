package com.cloud.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SocialUserVO implements Serializable{

    private static final long serialVersionUID = 4881294988435707435L;

    private String openId;

    /** 三方类型，比如QQ 微博 百度等 */
    private String providerId;
}

