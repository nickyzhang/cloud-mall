package com.cloud.api;

import com.cloud.common.cache.service.CacheService;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.CacheTimeUtils;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.common.redis.service.RedisService;
import com.cloud.dto.catalog.SkuDto;
import com.cloud.model.catalog.Sku;
import com.cloud.model.inventory.Inventory;
import com.cloud.service.SkuService;
import com.cloud.vo.catalog.ItemSpec;
import com.cloud.vo.catalog.SkuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/catalog/sku")
public class SkuController {

    @Autowired
    SkuService skuService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    GenIdService genIdService;

    @Autowired
    CacheService localCache;

    @Autowired
    RedisService redisService;

    /** 分布式事务*/
    @PostMapping("/add")
    public ResponseResult save(@RequestBody SkuVo skuVo){
        ResponseResult result = new ResponseResult();
        if (skuVo == null) {
            return result.failed("要添加的sku为空");
        }

        SkuDto skuDto = BeanUtils.copy(skuVo, SkuDto.class);
        skuDto.setCreateTime(LocalDateTime.now());
        skuDto.setCreateTime(LocalDateTime.now());
        int code = this.skuService.save(skuDto);
        if (code == 0) {
            log.debug("[Catalog][SkuController] 添加sku失败");
            return result.failed("添加sku失败");
        }

        Inventory inventory = skuVo.getInventory();
        if (inventory != null) {
            inventory.setCreateTime(LocalDateTime.now());
            inventory.setUpdateTime(LocalDateTime.now());
            ResponseResult stockResult = this.inventoryService.add(inventory);
            if (stockResult.getCode() != ResultCodeEnum.OK.getCode()) {
                return result.failed("添加sku库存失败");
            }
        }
        // 添加到缓存中
        String key = "catalog:sku:"+skuVo.getId();
        this.localCache.put("catalogCache",key,skuVo);
        this.redisService.set(key,skuVo, CacheTimeUtils.generateCacheRandomTime(10,60));
        return code == 0 ? result.failed("添加sku失败") : result.success("添加sku成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody SkuVo skuVo){
        ResponseResult result = new ResponseResult();
        if (skuVo == null) {
            return result.failed("要更新的sku为空");
        }
        SkuDto skuDto = BeanUtils.copy(skuVo, SkuDto.class);
        String key = "catalog:sku:"+skuVo.getId();
        this.localCache.remove("catalogCache",key);
        this.redisService.del(key);
        int code = this.skuService.update(skuDto);
        if (code == 1) {
            this.localCache.put("catalogCache",key,skuVo);
            this.redisService.set(key,skuVo, CacheTimeUtils.generateCacheRandomTime(10,60));
            return result.success("更新sku成功");
        }
        return result.failed("更新sku失败");
    }

    @PostMapping("/delete/{skuId}")
    public ResponseResult delete(@PathVariable Long skuId){
        ResponseResult result = new ResponseResult();
        String key = "catalog:sku:"+skuId;
        this.localCache.remove("catalogCache",key);
        this.redisService.del(key);
        int code = this.skuService.delete(skuId);
        return code == 0 ? result.failed("删除sku失败") : result.success("删除sku成功");
    }

    @GetMapping("/list/{skuId}")
    public ResponseResult find(@PathVariable Long skuId){
        ResponseResult result = new ResponseResult();
        if (skuId == null) {
            log.debug("[Catalog][SkuController] skuId is null");
            return result;
        }
        SkuVo skuVo = null;
        String key = "catalog:sku:"+skuId;
        skuVo = (SkuVo)this.redisService.get(key);
        if (skuVo != null) {
            log.info("我是从Redis二级缓存中获取， SKU => "+JSONUtils.objectToJson(skuVo));
        }
        if (skuVo == null) {
            skuVo = (SkuVo)this.localCache.get("catalogCache",key);
            if (skuVo != null) {
                this.redisService.set(key,skuVo,CacheTimeUtils.generateCacheRandomTime(10,60));
            }
            if (skuVo != null) {
                log.info("我是从本地一级缓存中获取， SKU => "+JSONUtils.objectToJson(skuVo));
            }
        }

        if (skuVo == null) {
            skuVo = this.skuService.find(skuId);
            if (skuVo != null) {
                this.localCache.put("catalogCache",key,skuVo);
                this.redisService.set(key,skuVo,CacheTimeUtils.generateCacheRandomTime(10,60));
            }
            if (skuVo != null) {
                log.info("我是从数据库查询获取， SKU => "+JSONUtils.objectToJson(skuVo));
            }
        }

        return skuVo == null ? result.failed("不存在这个sku") : result.success(skuVo);
    }

    @RequestMapping("/findAll")
    public ResponseResult<List<Sku>> findAll(){
        ResponseResult<List<Sku>> result = new ResponseResult<List<Sku>>();
        List<Sku> skuList = this.skuService.findAll();
        return CollectionUtils.isEmpty(skuList) ? result.failed("没有记录") : result.success("成功",skuList);
    }

    @RequestMapping("/count")
    public ResponseResult count(){
        Long count = this.skuService.count();
        ResponseResult result = new ResponseResult();
        return result.success(count);
    }

    @GetMapping(value = "/findSkuListByProductId")
    public ResponseResult findSkuListByProductId(@RequestParam("productId") Long productId){
        List<Sku> skuList = this.skuService.findSkuListByProductId(productId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(skuList)) {
            result.success(skuList);
        } else {
            result.failed("当前产品没有sku");
        }
        return result;
    }

    @GetMapping(value = "/findSkuListByBundleId")
    public ResponseResult findSkuListByBundleId(@RequestParam("bundleId") Long bundleId){
        List<Sku> skuList = this.skuService.findSkuListByBundleId(bundleId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(skuList)) {
            result.success(skuList);
        } else {
            result.failed("当前bundle没有sku信息");
        }
        return result;
    }


    @GetMapping("/findAttributes")
    public ResponseResult findAttributes(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        Map<String,Object> properties = this.skuService.findProperties(skuId);
        if (MapUtils.isNotEmpty(properties)) {
            result.success(properties);
        } else {
            result.failed("该sku没有属性");
        }
        return result;
    }

    @GetMapping("/findSaleAttributes")
    public ResponseResult findSaleAttributes(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        Map<String,Object> properties = this.skuService.findSalesProperties(skuId);
        if (MapUtils.isNotEmpty(properties)) {
            result.success(properties);
        } else {
            result.failed("该sku没有销售属性");
        }
        return result;
    }

    @GetMapping("/findItemSpecList")
    public ResponseResult findItemSpecList(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        Map<String,Object> properties = this.skuService.findSalesProperties(skuId);
        if (MapUtils.isEmpty(properties)) {
            return result.failed("该SKU没有规格信息");
        }
        List<ItemSpec> specList = new ArrayList<>();
        ItemSpec spec = new ItemSpec();
        properties.forEach( (k,v) -> {
            spec.setSkuId(skuId);
            spec.setAttrName(k);
            spec.setAttrValue(v);
            specList.add(spec);
        });
        return result.success("成功",specList);
    }

    @GetMapping(value = "/getAvailableStock")
    public ResponseResult getAvailableStock(@RequestParam("skuId") Long skuId) {
        return new ResponseResult().success("成功",100);
//        return this.inventoryService.getInventoryBySkuId(skuId);
    }

    @RequestMapping(value = "/getPrice",method = RequestMethod.GET)
    public ResponseResult getPrice(@RequestParam("skuId") Long skuId){
        ResponseResult<BigDecimal> result = new ResponseResult<BigDecimal>();
        SkuVo skuVo = this.skuService.find(skuId);
        if (skuVo == null) {
            return result.failed("没有这个商品");
        }
        return result.success("查询成功",BigDecimal.valueOf(skuVo.getSalePrice()/100));
    }
}
