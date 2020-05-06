package com.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
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
