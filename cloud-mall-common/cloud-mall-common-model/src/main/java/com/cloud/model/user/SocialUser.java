package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SocialUser implements Serializable{

    private static final long serialVersionUID = 4881294988435707435L;

    private Long id;

    private Long userId;

    private String openId;

    private String providerId;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}

