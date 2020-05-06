package com.cloud.service;

import com.cloud.model.inventory.InventoryFlow;

public interface InventoryFlowService {

    public int save(InventoryFlow flow);

    public int update(InventoryFlow flow);

    public int delete(InventoryFlow flow);

    public int deleteById(Long id);

    public InventoryFlow findById(Long id);

    public InventoryFlow findBySkuId(Long skuId);
}
