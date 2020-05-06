package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.aggr.DetailsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "/catalogcloud-mall-details")
public interface ProductDetailService {

    @RequestMapping(value = "/details/{skuId}",method = RequestMethod.GET)
    public ResponseResult<DetailsInfo> findProductDetails(@PathVariable("skuId") Long skuId);
}
