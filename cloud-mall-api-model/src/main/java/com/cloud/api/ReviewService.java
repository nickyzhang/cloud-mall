package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.review.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "/catalogcloud-mall-review")
public interface ReviewService {
    @RequestMapping(value = "/catalog/review/{id}",method = RequestMethod.GET)
    public ResponseResult<Review> findByReviewId(@PathVariable("id") Long reviewId);

    @RequestMapping(value = "/catalog/review/sku",method = RequestMethod.GET)
    public ResponseResult<List<Review>> findBySkuId(@RequestParam("skuId") Long skuId);
}
