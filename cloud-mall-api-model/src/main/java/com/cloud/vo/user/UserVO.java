package com.cloud.vo.user;

import com.cloud.model.user.BillingAddress;
import com.cloud.model.user.ShippingAddress;
import com.cloud.model.user.UserDetail;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User Entity
 *
 * @author nickyzhang
 * @since 0.0.1
 */
@Data
public class UserVO implements Serializable{

    private static final long serialVersionUID = -7174577569661431981L;

    private String username;

    private String password;

    private String confirmPassword;

    private String phone;

    private String smsCode;

    private String email;

    private String emailCode;

    private UserDetail userDetail;

    private List<ShippingAddress> shippingAddressList;

    private List<BillingAddress> billingAddressList;
}
