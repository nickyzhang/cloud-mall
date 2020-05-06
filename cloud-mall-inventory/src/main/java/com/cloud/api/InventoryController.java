package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.annotation.PerformanceMonitor;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.CacheTimeUtils;
import com.cloud.exception.InventoryBizException;
import com.cloud.model.inventory.Inventory;
import com.cloud.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    InventoryService inventoryService;

    @PostMapping("/add")
    @PerformanceMonitor
    public ResponseResult add(@RequestBody Inventory inventory) {
        LOGGER.info("[InventoryController] 添加库存信息");
        long startTime = System.currentTimeMillis();
        if (inventory == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }

        if (inventory.getTotalStock() <= 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_ADD_NUM_LESS_THAN_ZERO.getCode(),
                    ResultCodeEnum.INVENTORY_ADD_NUM_LESS_THAN_ZERO.getMessage());
        }

        int resultCode = this.inventoryService.save(inventory);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_ADD_FAILED.getMessage());
        }
        long endTime = System.currentTimeMillis();
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "添加库存成功");
    }

    @PostMapping("/batchAdd")
    public ResponseResult addList(@RequestBody List<Inventory> inventoryList) {
        if (CollectionUtils.isEmpty(inventoryList)) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }

        int resultCode = this.inventoryService.saveList(inventoryList);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_ADD_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "批量添加库存成功");
    }

    @GetMapping("/list/{id}")
    public ResponseResult getInventoryById(@RequestParam("id") Long id){
        Inventory inventory = this.inventoryService.findById(id);
        if (inventory == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }
        ResponseResult result = new ResponseResult();
        return result.success(inventory);
    }

    @GetMapping("/findBySkuId")
    @PerformanceMonitor
    public ResponseResult getInventoryBySkuId(@RequestParam("skuId") Long skuId){
        log.info("获取SkuID: {} 的库存", skuId);
        Inventory inventory = this.inventoryService.findBySkuId(skuId);
        if (inventory == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }
        long random = CacheTimeUtils.generateCacheRandomTime(400, 1300);
        log.info("随机时间: {} ",random);
        if (random > 700 && random < 1000) {
            int res = 100 / 0;
        } else if(random > 1000) {
            try {
                TimeUnit.MILLISECONDS.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        int r = new Random().nextInt(800);
//        if (r < 400) {
//            r += 300;
//        }
//        try {
//            TimeUnit.MILLISECONDS.sleep(r);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ResponseResult result = new ResponseResult();
        return result.success(inventory);
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody Inventory inventory) {
        if (inventory == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }

        int resultCode = this.inventoryService.update(inventory);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_ADD_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "更新库存成功");
    }

    @DeleteMapping("/delete")
    public ResponseResult delete(Inventory inventory) {
        if (inventory == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_NULL.getMessage());
        }

        int resultCode = this.inventoryService.delete(inventory);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPDATE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_UPDATE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        int resultCode = this.inventoryService.deleteById(id);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存成功");
    }

    /**
     * 预先占用库存，但是不真正扣减总库存，真正扣减发生在支付的时候
     * 实现方式可以通过MySQL乐观锁或者Redis来实现
     * @param skuId
     * @param stockNum
     * @return
     */
    @PostMapping(value = "/deduct/available-stock")
    @ApiOperation(value = "预先占用库存")
    @PerformanceMonitor
    public ResponseResult preOccupyStock(@RequestParam("skuId") Long skuId, @RequestParam Integer stockNum) {
        ResponseResult result = new ResponseResult();
        // 对扣减可用库存进行参数校验
        if (stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_DEDUCT_LESS_THAN_ZERO.getCode(),
                    ResultCodeEnum.INVENTORY_DEDUCT_LESS_THAN_ZERO.getMessage());
        }

        // 对可用库存进行扣减，对占用库存进行递增,并不真正的扣减总库存
        int resultCode = this.inventoryService.updateAvailableStockOptimistic(skuId, stockNum);
        if (resultCode == 0) {
            result.failed(String.format("%d: 没有可用库存,扣减可用库存失败!",skuId));
            return result;
        }
        return result.success("扣减有效库存成功");
    }

    /**
     * 真正扣减库存
     * 实现方式可以通过MySQL乐观锁或者Redis来实现
     * @param skuId
     * @param stockNum
     * @return
     */
    @PostMapping("/deduct/total-stock")
    @PerformanceMonitor
    public ResponseResult deductTotalStock(@RequestParam("skuId") Long skuId, @RequestParam Integer stockNum) {
        ResponseResult result = new ResponseResult();
        if (stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_DEDUCT_LESS_THAN_ZERO.getCode(),
                    ResultCodeEnum.INVENTORY_DEDUCT_LESS_THAN_ZERO.getMessage());
        }

        int resultCode = this.inventoryService.updateTotalStockOptimistic(skuId, stockNum);
        if (resultCode == 0) {
            if (resultCode == 0) {
                return result.failed(String.format("%d: 没有冻结库存,扣减总库存失败!",skuId));
            }
        }
        return result.success("扣减总库存成功");
    }

    @PostMapping("/threshold")
    public ResponseResult getStockThreshold(@RequestParam("skuId") Long skuId) {

        Inventory inventory = this.inventoryService.findBySkuId(skuId);
        int threshold = inventory.getStockThreshold();
        return new ResponseResult().success(threshold);
    }
}
