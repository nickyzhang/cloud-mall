package com.cloud.web.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderSubmitReq implements Serializable {

    private Long userId;

    private List<Long> skuList;
}
