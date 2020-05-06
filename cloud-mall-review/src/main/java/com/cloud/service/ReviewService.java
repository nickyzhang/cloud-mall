package com.cloud.service;

import com.cloud.model.review.Review;
import java.util.List;

public interface ReviewService {

    public Review findReviewById(Long reviewId);

    public List<Review> findReviewBySkuId(Long skuId);
}
