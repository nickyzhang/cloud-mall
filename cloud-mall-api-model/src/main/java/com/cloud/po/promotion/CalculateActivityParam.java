package com.cloud.po.promotion;

import com.cloud.model.promotion.Activity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculateActivityParam implements Serializable {

    private static final long serialVersionUID = 6164711355033889043L;

    private List<Activity> activityList;

    private Integer pieces;

    private BigDecimal amount;
}
