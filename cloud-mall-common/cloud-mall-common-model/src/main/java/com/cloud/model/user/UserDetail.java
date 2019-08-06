package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDetail implements Serializable{

    private static final long serialVersionUID = 9059489343325973473L;

    private Long id;

    private Long userId;

    private String gender;

    private int age;

    private LocalDate birthday;

    private LocalDateTime registerTime;

    private String registerIp;

    private LocalDateTime lastLoginTime;

    private String lastLoginIp;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
