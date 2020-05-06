package com.cloud.service.impl;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.redis.service.RedisService;
import com.cloud.enums.StockType;
import com.cloud.exception.InventoryBizException;
import com.cloud.mapper.InventoryMapper;
import com.cloud.model.inventory.Inventory;
import com.cloud.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    RedisService redisService;

    @Override
    public int save(Inventory inventory) {

        return this.inventoryMapper.save(inventory);
    }

    @Override
    public int saveList(List<Inventory> inventoryList) {

        return this.inventoryMapper.saveList(inventoryList);
    }

    @Override
    public int update(Inventory inventory) {

        return this.inventoryMapper.update(inventory);
    }

    @Override
    public int delete(Inventory inventory) {

        return this.inventoryMapper.delete(inventory);
    }

    @Override
    public int deleteById(Long id) {

        return this.inventoryMapper.deleteById(id);
    }

    @Override
    public Inventory findById(Long id) {

        return this.inventoryMapper.findById(id);
    }

    @Override
    public Inventory findBySkuId(Long skuId) {

        return this.inventoryMapper.findBySkuId(skuId);
    }

    @Override
    public int deductAvailableStock(Map<String, Object> map) {

        return this.inventoryMapper.deductAvailableStock(map);
    }

    @Override
    public int deductTotalStock(Map<String, Object> map) {

        return this.inventoryMapper.deductTotalStock(map);
    }

    @Override
    public Inventory checkAvailableStock(Long skuId, Integer stockNum) {
        Inventory inventory = this.findBySkuId(skuId);
        if (inventory == null || inventory.getAvailableStock() <= 0) {
            return null;
        }
        if (inventory.getAvailableStock() - stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_AVAILABLE_INSUFFICIENT.getCode(),
                    ResultCodeEnum.INVENTORY_AVAILABLE_INSUFFICIENT.getMessage());
        }
        return inventory;
    }

    @Override
    public Inventory checkFrozenStock(Long skuId, Integer stockNum) {
        Inventory inventory = this.findBySkuId(skuId);
        if (inventory == null) {
            return null;
        }
        if (inventory.getFrozenStock() - stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FROZEN_INSUFFICIENT.getCode(),
                    ResultCodeEnum.INVENTORY_FROZEN_INSUFFICIENT.getMessage());
        }
        return inventory;
    }

    @Override
    public Inventory checkAvailableStockByRedis(Long skuId, Integer stockNum) {
        Inventory inventory = (Inventory)this.redisService.get("stock:"+skuId);
        if (inventory == null || inventory.getAvailableStock() <= 0) {
            return null;
        }
        if (inventory.getAvailableStock() - stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_INSUFFICIENT.getCode(),
                    ResultCodeEnum.INVENTORY_INSUFFICIENT.getMessage());
        }
        return inventory;
    }

    @Override
    public Inventory checkFrozenStockByRedis(Long skuId, Integer stockNum) {
        Inventory inventory = (Inventory)this.redisService.get("stock:"+skuId);
        if (inventory == null) {
            return null;
        }
        if (inventory.getFrozenStock() - stockNum < 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_INSUFFICIENT.getCode(),
                    ResultCodeEnum.INVENTORY_INSUFFICIENT.getMessage());
        }
        return inventory;
    }

    @Override
    public int updateAvailableStockOptimistic(Long skuId, Integer stockNum) {
        Inventory inventory = checkAvailableStock(skuId,stockNum);
        // 如果没有这个sku的库存信息或者可用存数小于0，则不允许发生扣减
        if (inventory == null) {
            return 0;
        }
        Integer availableStock = inventory.getAvailableStock() - stockNum;
        Integer frozenStock = inventory.getFrozenStock() + stockNum;
        Long version = inventory.getVersion();
        Long id = inventory.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("availableStock",availableStock);
        map.put("frozenStock",frozenStock);
        map.put("version",version);
        return this.deductAvailableStock(map);
    }

    @Override
    public int updateAvailableStockOptimisticByRedis(Long skuId, Integer stockNum) {
        Inventory inventory = checkAvailableStockByRedis(skuId,stockNum);
        // 如果没有这个sku的库存信息或者可用存数小于0，则不允许发生扣减
        if (inventory == null) {
            return 0;
        }
        Integer availableStock = inventory.getAvailableStock() - stockNum;
        Integer frozenStock = inventory.getFrozenStock() + stockNum;
        Long version = inventory.getVersion();
        Long id = inventory.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("availableStock",availableStock);
        map.put("frozenStock",frozenStock);
        map.put("version",version);
        int count = this.deductAvailableStock(map);
        // 表示版本变化，所以没有更新成功
        if (count == 0) {
            return 0;
        }
        this.redisService.set("stock:"+inventory.getSkuId(),inventory);
        return 1;
    }

    @Override
    public int updateTotalStockOptimistic(Long skuId, Integer stockNum) {
        Inventory inventory = checkFrozenStock(skuId,stockNum);
        // 如果没有这个sku的库存信息或者冻结库存数小于0，则不允许发生扣减
        if (inventory == null) {
            return 0;
        }
        Integer totalStock = inventory.getTotalStock() - stockNum;
        Integer frozenStock = inventory.getFrozenStock() - stockNum;
        Long version = inventory.getVersion();
        Long id = inventory.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("totalStock",totalStock);
        map.put("frozenStock",frozenStock);
        map.put("version",version);
        return this.deductTotalStock(map);
    }

    @Override
    public int updateTotalStockOptimisticByRedis(Long skuId, Integer stockNum) {
        Inventory inventory = checkFrozenStockByRedis(skuId,stockNum);
        // 如果没有这个sku的库存信息或者冻结库存数小于0，则不允许发生扣减
        if (inventory == null) {
            return 0;
        }
        Integer totalStock = inventory.getTotalStock() - stockNum;
        Integer frozenStock = inventory.getFrozenStock() - stockNum;
        Long version = inventory.getVersion();
        Long id = inventory.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("totalStock",totalStock);
        map.put("frozenStock",frozenStock);
        map.put("version",version);
        int count = this.deductTotalStock(map);
        // 表示版本变化，所以没有更新成功
        if (count == 0) {
            return 0;
        }
        this.redisService.set("stock:"+inventory.getSkuId(),inventory);
        return 1;
    }
}
