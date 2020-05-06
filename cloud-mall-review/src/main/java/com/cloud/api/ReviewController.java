package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.CacheTimeUtils;
import com.cloud.model.review.Review;
import com.cloud.service.ReviewService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/review/{id}")
    public ResponseResult<Review> findByReviewId(@PathVariable("id") Long reviewId) {
        ResponseResult<Review> result = new ResponseResult<>();
        Review review = this.reviewService.findReviewById(reviewId);
        return review == null ? result.failed("不存在这个评论") : result.success(review);
    }

    @GetMapping("/review/sku")
    public ResponseResult<List<Review>> findBySkuId(@RequestParam("skuId") Long skuId) {
        ResponseResult<List<Review>> result = new ResponseResult<>();
        List<Review> reviewList = this.reviewService.findReviewBySkuId(skuId);
        try {
            Thread.sleep(CacheTimeUtils.generateCacheRandomTime(1000,2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CollectionUtils.isEmpty(reviewList) ? result.failed("当前SKU没有评论") : result.success(reviewList);
    }
}
