package com.cloud.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserAddressVO implements Serializable{

    private static final long serialVersionUID = 5771992853045487307L;

    private String country;

    private String province;

    private String city;

    private String area;

    private String details;

}
