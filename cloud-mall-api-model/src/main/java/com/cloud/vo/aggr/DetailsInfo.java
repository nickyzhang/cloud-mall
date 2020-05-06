package com.cloud.vo.aggr;

import com.cloud.model.review.Review;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DetailsInfo implements Serializable {

    private static final long serialVersionUID = -3867647186804245720L;

    private SkuInfo skuInfo;

    private List<Review> reviewList;
}
