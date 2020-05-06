package com.cloud.vo.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ItemSpec implements Serializable{

    private static final long serialVersionUID = -5986341265206272331L;

    private Long skuId;

    private String attrName;

    private Object attrValue;
}
