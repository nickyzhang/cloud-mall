package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.ProductDto;
import com.cloud.model.catalog.Product;
import com.cloud.service.ProductService;
import com.cloud.vo.catalog.ProductVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalog/prod")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseResult save(@RequestBody ProductVo productVo){
        ResponseResult result = new ResponseResult();
        if (productVo == null) {
            return result.failed("要添加的产品为空");
        }
        ProductDto productDto = BeanUtils.copy(productVo, ProductDto.class);
        int code = this.productService.save(productDto);
        return code == 0 ? result.failed("添加产品失败") : result.success("添加产品成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody ProductVo productVo){
        ResponseResult result = new ResponseResult();
        if (productVo == null) {
            return result.failed("要更新的产品为空");
        }
        ProductDto productDto = BeanUtils.copy(productVo, ProductDto.class);
        int code = this.productService.update(productDto);
        return code == 0 ? result.failed("更新产品失败") : result.success("更新产品成功");
    }

    @PostMapping("/delete/{productId}")
    public ResponseResult delete(@PathVariable Long productId){
        ResponseResult result = new ResponseResult();
        int code = this.productService.delete(productId);
        return code == 0 ? result.failed("删除产品失败") : result.success("删除产品成功");
    }

    @GetMapping("/list/{productId}")
    public ResponseResult find(@PathVariable Long productId){
        ResponseResult result = new ResponseResult();
        ProductDto productDto = this.productService.find(productId);
        return productDto == null ? result.failed("没有这个产品") : result.success(productDto);
    }

    @RequestMapping("/findAll")
    public ResponseResult findAll(){
        ResponseResult result = new ResponseResult();
        List<Product> productList = this.productService.findAll();
        if (CollectionUtils.isNotEmpty(productList)) {
            result.success(productList);
        } else {
            result.failed("没有产品信息");
        }
        return result;
    }

    @RequestMapping("/count")
    public ResponseResult count(){
        Long count = this.productService.count();
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg("操作成功");
        result.setData(count);
        return result;
    }

    @GetMapping(value = "/findProductListByBrandId")
    public ResponseResult findProductListByBrandId(@RequestParam("brandId") Long brandId){
        List<Product> productList = this.productService.findProductListByBrandId(brandId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(productList)) {
            result.success(productList);
        } else {
            result.failed("这个品牌没有任何产品");
        }
        return result;
    }

    @GetMapping(value = "/findProductListByCategoryId")
    public ResponseResult findProductListByCategoryId(@RequestParam("categoryId") Long categoryId){
        List<Product> productList = this.productService.findProductListByCategoryId(categoryId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(productList)) {
            result.success(productList);
        } else {
            result.failed("这个分类没有任何产品");
        }
        return result;
    }

    @GetMapping("/findAttributes")
    public ResponseResult findAttributes(@RequestParam("productId") Long productId) {
        ResponseResult result = new ResponseResult();
        Map<String,Object> properties = this.productService.findProperties(productId);
        if (MapUtils.isNotEmpty(properties)) {
            result.success(properties);
        } else {
            result.failed("该产品没有属性");
        }
        return result;
    }
}
