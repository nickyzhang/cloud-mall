package com.cloud.mapper;

import com.cloud.model.inventory.InventoryFlow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryFlowMapper {

    public int save(InventoryFlow flow);

    public int update(InventoryFlow flow);

    public int delete(InventoryFlow flow);

    public int deleteById(Long id);

    public InventoryFlow findById(Long id);

    public InventoryFlow findBySkuId(Long skuId);
}
