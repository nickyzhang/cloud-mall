package com.cloud;

import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.DateUtils;
import com.cloud.dto.catalog.ProductDto;
import com.cloud.dto.catalog.SkuDto;
import com.cloud.model.catalog.AttributeValue;
import com.cloud.model.catalog.Product;
import com.cloud.model.catalog.Sku;
import com.cloud.service.*;
import com.cloud.vo.catalog.ProductVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudCatalogApplicationTest {

    @Autowired
    ProductService productService;

    @Autowired
    SkuService skuService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    AttributeNameService attributeNameService;

    @Autowired
    AttributeValueService attributeValueService;

    @Autowired
    GenIdService idService;

    @Test
    public void addProduct() {
//        ProductDto product = new ProductDto();
//        product.setId(idService.genId());
//        product.setName("Huawei/华为 Mate 20 X (5G)");
//        product.setDescription("Huawei/华为 Mate 20 X (5G)");
//        product.setRating(4.5F);
//        product.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/2838892713/O1CN01cx4C4x1Vub4bU5pwO_!!2838892713.jpg");
//        product.setDisplayOrder(0);
//        product.setReview(5461);
//        LocalDateTime now = LocalDateTime.now();
//        product.setCreateTime(now);
//        product.setUpdateTime(now);
//        LocalDateTime startTime = DateUtils.pasreToDateTime("2019-10-01 00:00:00","yyyy-MM-dd HH:mm:ss");
//        LocalDateTime endTime = DateUtils.pasreToDateTime("2019-10-07 23:59:59","yyyy-MM-dd HH:mm:ss");
//        product.setStartTime(startTime);
//        product.setEndTime(endTime);
//        product.setCategory(this.categoryService.find(117503439713337347L));
//        product.setBrand(this.brandService.find(117864366216314883L));
//        this.productService.save(product);
//
//        List<AttributeValue> productAttribueValuetList = new ArrayList<>();
//        productAttribueValuetList.add(this.attributeValueService.find(118140662536208387L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198588419L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198591491L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198600707L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198601731L));
//        product.setProperties(productAttribueValuetList);
//        productService.update(product);
//
//        SkuDto mobile1 = new SkuDto();
//        mobile1.setId(idService.genId());
//        mobile1.setName("Huawei/华为 Mate 20 X (5G)全面屏超大广角徕卡镜头麒麟980智能手机5G手机mate20x5g手机 绿色 256G");
//        mobile1.setDescription("Huawei/华为 Mate 20 X (5G)全面屏超大广角徕卡镜头麒麟980智能手机5G手机mate20x5g手机 绿色 256G");
//        mobile1.setCostPrice(300000L);
//        mobile1.setListPrice(619900L);
//        mobile1.setSalePrice(619900L);
//        mobile1.setDisplayOrder(0);
//        mobile1.setStatus(true);
//        now = LocalDateTime.now();
//        mobile1.setCreateTime(now);
//        mobile1.setUpdateTime(now);
//        mobile1.setStartTime(startTime);
//        mobile1.setEndTime(endTime);
//        mobile1.setDeleted(false);
//        mobile1.setCode("190214");
//        mobile1.setBarCode("6931659892036");
//        mobile1.setProduct(product);
//        this.skuService.save(mobile1);
//
//        List<AttributeValue> mobile1AttribueValuetList = new ArrayList<>();
//        productAttribueValuetList.add(this.attributeValueService.find(118166813518351363L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198604803L));
//        mobile1.setProperties(mobile1AttribueValuetList);
//        this.skuService.update(mobile1);
//
//        SkuDto mobile2 = new SkuDto();
//        mobile2.setId(idService.genId());
//        mobile2.setName("Huawei/华为 Mate 20 X (5G)全面屏超大广角徕卡镜头麒麟980智能手机5G手机mate20x5g手机 红色 128G");
//        mobile2.setDescription("Huawei/华为 Mate 20 X (5G)全面屏超大广角徕卡镜头麒麟980智能手机5G手机mate20x5g手机 红色 128G");
//        mobile2.setCostPrice(200000L);
//        mobile2.setListPrice(509900L);
//        mobile2.setSalePrice(500000L);
//        mobile2.setDisplayOrder(1);
//        mobile2.setStatus(true);
//        now = LocalDateTime.now();
//        mobile2.setCreateTime(now);
//        mobile2.setUpdateTime(now);
//        mobile2.setStartTime(startTime);
//        mobile2.setEndTime(endTime);
//        mobile2.setCode("190215");
//        mobile2.setBarCode("6931659892037");
//        mobile2.setDeleted(false);
//        mobile2.setProduct(product);
//        this.skuService.save(mobile2);
//
//        List<AttributeValue> mobile2AttribueValuetList = new ArrayList<>();
//        productAttribueValuetList.add(this.attributeValueService.find(118166813518349315L));
//        productAttribueValuetList.add(this.attributeValueService.find(118164974198605827L));
//        mobile2.setProperties(mobile1AttribueValuetList);
//        this.skuService.update(mobile2);
//
//        List<Sku> skuList = new ArrayList<>();
//        skuList.add(mobile1);
//        skuList.add(mobile2);
//        product.setSkuList(skuList);
//        this.productService.update(product);
    }
}
