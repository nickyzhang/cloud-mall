package com.cloud.service.impl;

import com.cloud.model.review.Review;
import com.cloud.service.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Override
    public Review findReviewById(Long reviewId) {
        return null;
    }

    @Override
    public List<Review> findReviewBySkuId(Long skuId) {
        return null;
    }
}
