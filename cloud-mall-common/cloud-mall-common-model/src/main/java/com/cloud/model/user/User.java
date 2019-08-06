package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User Entity
 *
 * @author nickyzhang
 * @since 0.0.1
 */
@Data
public class User implements Serializable{

    private static final long serialVersionUID = -7174577569661431981L;

    private Long userId;

    private String username;

    private String password;

    private String salt;

    private String phone;

    private String email;

    private String nickname;

    private String avator;

    private Integer status;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public User() {}

    public User(Long userId, String username, String password, String salt, String phone, String email, String nickname, String avator, Integer status, LocalDateTime createDate, LocalDateTime updateDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.avator = avator;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

}
