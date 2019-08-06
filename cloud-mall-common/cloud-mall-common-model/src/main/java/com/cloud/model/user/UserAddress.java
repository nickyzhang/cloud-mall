package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author nickyzhang
 */
@Data
public class UserAddress implements Serializable{

    private static final long serialVersionUID = 5771992853045487307L;

    private Long addressId;

    private Long userId;

    private String country;

    private String province;

    private String city;

    private String area;

    private String details;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
