package com.cloud.mapper;

import com.cloud.model.inventory.Inventory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InventoryMapper {

    public int save(Inventory inventory);

    public int saveList(List<Inventory> inventoryList);

    public int update(Inventory inventory);

    public int delete(Inventory inventory);

    public int deleteById(Long id);

    public Inventory findById(Long id);

    public Inventory findBySkuId(Long skuId);

    public int deductAvailableStock(Map<String,Object> map);

    public int deductTotalStock(Map<String,Object> map);
}
