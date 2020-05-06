package com.cloud.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDetailVO implements Serializable{

    private static final long serialVersionUID = 9059489343325973473L;

    private Long userId;

    private String gender;

    private Integer age;

    private LocalDate birthday;
}
