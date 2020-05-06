package com.cloud.vo.user;

import com.cloud.model.user.BillingAddress;
import com.cloud.model.user.ShippingAddress;
import com.cloud.model.user.UserDetail;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5210143039643192055L;

    private Long userId;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String avator;

    private UserDetail userDetail;

    private List<ShippingAddress> shippingAddressList;

    private List<BillingAddress> billingAddressList;
}
