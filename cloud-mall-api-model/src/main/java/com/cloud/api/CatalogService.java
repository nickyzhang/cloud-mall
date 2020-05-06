package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.catalog.Sku;
import com.cloud.vo.catalog.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="cloud-mall-catalog")
public interface CatalogService {

    @RequestMapping(value = "/catalog/cat/add",method = RequestMethod.POST)
    public ResponseResult saveCat(@RequestBody CategoryVo categoryVo);

    @RequestMapping(value = "/catalog/cat/update",method = RequestMethod.PUT)
    public ResponseResult updateCat(@RequestBody CategoryVo categoryVo);

    @RequestMapping(value = "/catalog/cat/delete/{catId}",method = RequestMethod.POST)
    public ResponseResult deleteByCatId(@PathVariable("catId") Long catId);

    @RequestMapping(value = "/catalog/cat/find/{catId}",method = RequestMethod.GET)
    public ResponseResult findByCatId(@PathVariable("catId") Long catId);

    @RequestMapping(value = "/catalog/cat/findAll",method = RequestMethod.GET)
    public ResponseResult findAllCategroyList();

    @RequestMapping(value = "/catalog/cat/findByName",method = RequestMethod.GET)
    public ResponseResult findCatByName(@RequestParam("name") String name);

    @RequestMapping(value = "/catalog/cat/findCategoriesByBrandId",method = RequestMethod.GET)
    public ResponseResult findCategoriesByBrandId(@RequestParam("brandId") Long brandId);

    @RequestMapping(value = "/catalog/cat/findCategoryListByAttributeNameId",method = RequestMethod.GET)
    public ResponseResult findCategoryListByAttributeNameId(@RequestParam("attributeNameId") Long attributeNameId);

    @RequestMapping(value = "/catalog/brand/add",method = RequestMethod.POST)
    public ResponseResult saveBrand(@RequestBody BrandVo brandVo);

    @RequestMapping(value = "/catalog/brand/update",method = RequestMethod.PUT)
    public ResponseResult updateBrand(@RequestBody BrandVo brandVo);

    @RequestMapping(value = "/catalog/brand/delete/{brandId}",method = RequestMethod.POST)
    public ResponseResult deleteByBrandId(@PathVariable("brandId") Long brandId);

    @RequestMapping(value = "/catalog/brand/find/{brandId}",method = RequestMethod.GET)
    public ResponseResult findByBrandId(@PathVariable("brandId") Long brandId);

    @RequestMapping(value = "/catalog/brand/findAll",method = RequestMethod.GET)
    public ResponseResult findAllBrandList();

    @RequestMapping(value = "/catalog/brand/findByName",method = RequestMethod.GET)
    public ResponseResult findByBrandName(@RequestParam("name") String name);

    @RequestMapping(value = "/catalog/brand/findBrandsByCategoryId",method = RequestMethod.GET)
    public ResponseResult findBrandsByCategoryId(@RequestParam("catId") Long catId);

    @RequestMapping(value = "/catalog/prod/add",method = RequestMethod.POST)
    public ResponseResult saveProduct(@RequestBody ProductVo productVo);

    @RequestMapping(value = "/catalog/prod/update",method = RequestMethod.PUT)
    public ResponseResult updateProduct(@RequestBody ProductVo productVo);

    @RequestMapping(value = "/catalog/prod/delete/{id}",method = RequestMethod.POST)
    public ResponseResult deleteByProductId(@PathVariable("id") Long productId);

    @RequestMapping(value = "/catalog/prod/find/{id}",method = RequestMethod.GET)
    public ResponseResult findByProductId(@PathVariable("id") Long productId);

    @RequestMapping(value = "/catalog/prod/findAll",method = RequestMethod.GET)
    public ResponseResult findAllProductList();

    @RequestMapping(value = "/catalog/prod/findProductListByBrandId",method = RequestMethod.GET)
    public ResponseResult findProductListByBrandId(@RequestParam("brandId") Long brandId);

    @RequestMapping(value = "/catalog/prod/findProductListByCategoryId",method = RequestMethod.GET)
    public ResponseResult findProductListByCategoryId(@RequestParam("catId") Long catId);

    @RequestMapping(value = "/catalog/prod/findAttributes",method = RequestMethod.GET)
    public ResponseResult findProductAttributes(@RequestParam("productId") Long productId);

    @RequestMapping(value = "/catalog/sku/add",method = RequestMethod.POST)
    public ResponseResult saveSku(@RequestBody SkuVo skuVo);

    @RequestMapping(value = "/catalog/sku/update",method = RequestMethod.PUT)
    public ResponseResult updateSku(@RequestBody SkuVo skuVo);

    @RequestMapping(value = "/catalog/sku/delete/{id}",method = RequestMethod.POST)
    public ResponseResult deleteBySkuId(@PathVariable("id") Long skuId);

    @RequestMapping(value = "/catalog/sku/list/{id}",method = RequestMethod.GET)
    public ResponseResult findBySkuId(@PathVariable("id") Long skuId);

    @RequestMapping(value = "/catalog/sku/findAll",method = RequestMethod.GET)
    public ResponseResult<List<Sku>> findAllSkuList();

    @RequestMapping(value = "/catalog/sku/findSkuListByProductId",method = RequestMethod.GET)
    public ResponseResult findSkuListByProductId(@RequestParam("productId") Long productId);

    @RequestMapping(value = "/catalog/sku/findSkuListByBundleId",method = RequestMethod.GET)
    public ResponseResult findSkuListByBundleId(@RequestParam("bundleId") Long bundleId);

    @RequestMapping(value = "/catalog/sku/findSkuAttributes",method = RequestMethod.GET)
    public ResponseResult findSkuAttributes(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/sku/findSaleAttributes",method = RequestMethod.GET)
    public ResponseResult findSkuSaleAttributes(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/sku/findItemSpecList", method = RequestMethod.GET)
    public ResponseResult findItemSpecList(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/sku/getAvailableStock",method = RequestMethod.GET)
    public ResponseResult getAvailableStock(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/sku/getPrice",method = RequestMethod.GET)
    public ResponseResult getPrice(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/attrName/add",method=RequestMethod.POST)
    public ResponseResult saveAttributeName(@RequestBody AttributeNameVo attributeNameVo);

    @RequestMapping(value = "/catalog/attrName/update",method=RequestMethod.PUT)
    public ResponseResult updateAttributeName(@RequestBody AttributeNameVo attributeNameVo);

    @RequestMapping(value = "/catalog/attrName/delete/{attributeNameId}",method=RequestMethod.POST)
    public ResponseResult deleteByAttributeNameId(@PathVariable("attributeNameId") Long attributeNameId);

    @RequestMapping(value = "/catalog/attrName/list/{attributeNameId}",method=RequestMethod.GET)
    public ResponseResult findByAttributeNameId(@PathVariable("attributeNameId") Long attributeNameId);

    @RequestMapping(value = "/catalog/attrName/findAllByAttributeNameList",method=RequestMethod.GET)
    public ResponseResult findAllByAttributeNameList();

    @RequestMapping(value = "/catalog/attrValue/findAttributeNamesByCategoryId",method=RequestMethod.GET)
    public ResponseResult findAttributeNamesByCategoryId(Long categoryId);

    @RequestMapping(value = "/catalog/attrValue/add",method=RequestMethod.POST)
    public ResponseResult saveAttributeValue(@RequestBody AttributeValueVo attributeValueVo);

    @RequestMapping(value = "/catalog/attrValue/update",method=RequestMethod.PUT)
    public ResponseResult updateAttributeValue(@RequestBody AttributeValueVo attributeValueVo);

    @RequestMapping(value = "/catalog/attrValue/delete/{attributeValueId}",method=RequestMethod.POST)
    public ResponseResult deleteByAttributeValueId(@PathVariable("attributeValueId") Long attributeValueId);

    @RequestMapping(value = "/catalog/attrValue/list/{attributeValueId}",method=RequestMethod.GET)
    public ResponseResult findByAttributeValueId(@PathVariable("attributeValueId") Long attributeValueId);

    @RequestMapping(value = "/catalog/attrValue/findAllAttributeValueList",method=RequestMethod.GET)
    public ResponseResult findAllAttributeValueList();

    @RequestMapping(value = "/catalog/attrValue/findAttributeValueListByAttributeNameId",method=RequestMethod.GET)
    public ResponseResult findAttributeValueListByAttributeNameId(@RequestParam("attrNameId") Long attrNameId );

    @RequestMapping(value = "/catalog/attrValue/findAttributeValueListByProductId",method=RequestMethod.GET)
    public ResponseResult findAttributeValueListByProductId(@RequestParam("productId") Long productId );

    @RequestMapping(value = "/catalog/attrValue/findAttributeValueListBySkuId",method=RequestMethod.GET)
    public ResponseResult findAttributeValueListBySkuId(@RequestParam("skuId") Long skuId );

}
