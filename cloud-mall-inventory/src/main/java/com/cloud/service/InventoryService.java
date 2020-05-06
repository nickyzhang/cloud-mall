package com.cloud.service;

import com.cloud.enums.StockType;
import com.cloud.model.inventory.Inventory;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    public int save(Inventory inventory);

    public int saveList(List<Inventory> inventoryList);

    public int update(Inventory inventory);

    public int delete(Inventory inventory);

    public int deleteById(Long id);

    public Inventory findById(Long id);

    public Inventory findBySkuId(Long skuId);

    public int deductAvailableStock(Map<String,Object> map);

    public int deductTotalStock(Map<String,Object> map);

    public Inventory checkAvailableStock(Long skuId, Integer stockNum);

    public Inventory checkFrozenStock(Long skuId, Integer stockNum);

    public Inventory checkAvailableStockByRedis(Long skuId, Integer stockNum);

    public Inventory checkFrozenStockByRedis(Long skuId, Integer stockNum);

    public int updateAvailableStockOptimisticByRedis(Long skuId, Integer stockNum);

    public int updateTotalStockOptimisticByRedis(Long skuId, Integer stockNum);

    public int updateAvailableStockOptimistic(Long skuId, Integer stockNum);

    public int updateTotalStockOptimistic(Long skuId, Integer stockNum);
}
