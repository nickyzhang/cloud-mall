package com.cloud.api.fallback;

import com.cloud.api.CatalogService;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.catalog.Sku;
import com.cloud.vo.catalog.*;
import feign.hystrix.FallbackFactory;
import java.util.List;

public class CatalogServiceFallbackFactory implements FallbackFactory<CatalogService> {
    @Override
    public CatalogService create(Throwable throwable) {
        return new CatalogService() {
            @Override
            public ResponseResult saveCat(CategoryVo categoryVo) {
                return new ResponseResult().failed("保存品类失败");
            }

            @Override
            public ResponseResult updateCat(CategoryVo categoryVo) {
                return new ResponseResult().failed("更新品类失败");
            }

            @Override
            public ResponseResult deleteByCatId(Long catId) {
                return new ResponseResult().failed("删除品类失败");
            }

            @Override
            public ResponseResult findByCatId(Long catId) {
                return new ResponseResult().failed("根据ID查询品类失败");
            }

            @Override
            public ResponseResult findAllCategroyList() {
                return new ResponseResult().failed("查询所有品类失败");
            }

            @Override
            public ResponseResult findCatByName(String name) {
                return new ResponseResult().failed("根据名字查询所有品类失败");
            }

            @Override
            public ResponseResult findCategoriesByBrandId(Long brandId) {
                return new ResponseResult().failed("根据品牌ID查询所有品类失败");
            }

            @Override
            public ResponseResult findCategoryListByAttributeNameId(Long attributeNameId) {
                return null;
            }

            @Override
            public ResponseResult saveBrand(BrandVo brandVo) {
                return null;
            }

            @Override
            public ResponseResult updateBrand(BrandVo brandVo) {
                return null;
            }

            @Override
            public ResponseResult deleteByBrandId(Long brandId) {
                return null;
            }

            @Override
            public ResponseResult findByBrandId(Long brandId) {
                return null;
            }

            @Override
            public ResponseResult findAllBrandList() {
                return null;
            }

            @Override
            public ResponseResult findByBrandName(String name) {
                return null;
            }

            @Override
            public ResponseResult findBrandsByCategoryId(Long catId) {
                return null;
            }

            @Override
            public ResponseResult saveProduct(ProductVo productVo) {
                return null;
            }

            @Override
            public ResponseResult updateProduct(ProductVo productVo) {
                return null;
            }

            @Override
            public ResponseResult deleteByProductId(Long productId) {
                return null;
            }

            @Override
            public ResponseResult findByProductId(Long productId) {
                return null;
            }

            @Override
            public ResponseResult findAllProductList() {
                return null;
            }

            @Override
            public ResponseResult findProductListByBrandId(Long brandId) {
                return null;
            }

            @Override
            public ResponseResult findProductListByCategoryId(Long catId) {
                return null;
            }

            @Override
            public ResponseResult findProductAttributes(Long productId) {
                return null;
            }

            @Override
            public ResponseResult saveSku(SkuVo skuVo) {
                return new ResponseResult().failed("保存SKU失败");
            }

            @Override
            public ResponseResult updateSku(SkuVo skuVo) {
                return new ResponseResult().failed("更新SKU失败");
            }

            @Override
            public ResponseResult deleteBySkuId(Long skuId) {
                return new ResponseResult().failed("根据id删除SKU失败");
            }

            @Override
            public ResponseResult findBySkuId(Long skuId) {
                return new ResponseResult().failed("根据id查找SKU失败");
            }

            @Override
            public ResponseResult<List<Sku>> findAllSkuList() {
                return new ResponseResult().failed("查询所有SKU失败");
            }


            @Override
            public ResponseResult findSkuListByProductId(Long productId) {
                return new ResponseResult().failed("根据productId查询SKU失败");
            }

            @Override
            public ResponseResult findSkuListByBundleId(Long bundleId) {
                return new ResponseResult().failed("根据bundleId查询SKU失败");
            }

            @Override
            public ResponseResult findSkuAttributes(Long skuId) {
                return new ResponseResult().failed("根据skuId查找属性失败");
            }

            @Override
            public ResponseResult findSkuSaleAttributes(Long skuId) {
                return new ResponseResult().failed("根据skuId查找销售属性失败");
            }

            @Override
            public ResponseResult findItemSpecList(Long skuId) {
                return new ResponseResult().failed("根据skuId查找属性键值对失败");
            }

            @Override
            public ResponseResult getAvailableStock(Long skuId) {
                return new ResponseResult().failed("无法根据skuId获取库存");
            }

            @Override
            public ResponseResult getPrice(Long skuId) {
                return new ResponseResult().failed("无法根据skuId获取价格");
            }

            @Override
            public ResponseResult saveAttributeName(AttributeNameVo attributeNameVo) {
                return null;
            }

            @Override
            public ResponseResult updateAttributeName(AttributeNameVo attributeNameVo) {
                return null;
            }

            @Override
            public ResponseResult deleteByAttributeNameId(Long attributeNameId) {
                return null;
            }

            @Override
            public ResponseResult findByAttributeNameId(Long attributeNameId) {
                return null;
            }

            @Override
            public ResponseResult findAllByAttributeNameList() {
                return null;
            }

            @Override
            public ResponseResult findAttributeNamesByCategoryId(Long categoryId) {
                return null;
            }

            @Override
            public ResponseResult saveAttributeValue(AttributeValueVo attributeValueVo) {
                return null;
            }

            @Override
            public ResponseResult updateAttributeValue(AttributeValueVo attributeValueVo) {
                return null;
            }

            @Override
            public ResponseResult deleteByAttributeValueId(Long attributeValueId) {
                return null;
            }

            @Override
            public ResponseResult findByAttributeValueId(Long attributeValueId) {
                return null;
            }

            @Override
            public ResponseResult findAllAttributeValueList() {
                return null;
            }

            @Override
            public ResponseResult findAttributeValueListByAttributeNameId(Long attrNameId) {
                return null;
            }

            @Override
            public ResponseResult findAttributeValueListByProductId(Long productId) {
                return null;
            }

            @Override
            public ResponseResult findAttributeValueListBySkuId(Long skuId) {
                return null;
            }
        };
    }
}
