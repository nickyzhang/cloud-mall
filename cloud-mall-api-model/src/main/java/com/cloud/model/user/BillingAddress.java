package com.cloud.model.user;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class BillingAddress extends BaseModel {

    private static final long serialVersionUID = -7954840984310183774L;

    private Long addressId;

    private Long userId;

    private String country;

    private String province;

    private String city;

    private String area;

    private String details;
}
