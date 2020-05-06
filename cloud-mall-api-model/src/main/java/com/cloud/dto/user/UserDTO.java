package com.cloud.dto.user;

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
public class UserDTO implements Serializable{

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

}
