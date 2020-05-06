package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.model.inventory.Inventory;
import com.cloud.model.review.Review;
import com.cloud.vo.aggr.DetailsInfo;
import com.cloud.vo.aggr.SkuInfo;
import com.cloud.vo.catalog.SkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductDetailController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    InventoryService inventoryService;

//    @Autowired
//    ShoppingCartService shoppingCartService;
//
    @Autowired
    ReviewService reviewService;


    @GetMapping("/details/{skuId}")
    public ResponseResult<DetailsInfo> findProductDetails(@PathVariable("skuId") Long skuId){
        ResponseResult<DetailsInfo> detailsResult = new ResponseResult<>();
        ResponseResult skuResult = this.catalogService.findBySkuId(skuId);
        DetailsInfo detailsInfo = new DetailsInfo();
        if (skuResult.getCode() != ResultCodeEnum.OK.getCode()) {
            return detailsResult.failed("无法获取该商品: "+skuId);
        }
        SkuVo skuVo = (SkuVo) skuResult.getData();
        SkuInfo skuInfo = BeanUtils.copy(skuVo,SkuInfo.class);

        ResponseResult stockResult = this.inventoryService.getInventoryBySkuId(skuId);
        if (skuResult.getCode() == ResultCodeEnum.OK.getCode()) {
            Inventory inv = (Inventory) stockResult.getData();
            skuInfo.setInventory(inv);
        }
        detailsInfo.setSkuInfo(skuInfo);

        ResponseResult<List<Review>> reviewsResult = this.reviewService.findBySkuId(skuId);
        if (skuResult.getCode() == ResultCodeEnum.OK.getCode()) {
            List<Review> reviews = (List<Review>) stockResult.getData();
            detailsInfo.setReviewList(reviews);
        }
        return detailsResult.success(detailsInfo);
    }
}
