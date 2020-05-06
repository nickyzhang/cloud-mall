package com.cloud.dto.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1117169078786016354L;

    private String orderNo;

    private String subject;

    private String paymentAmount;
}
