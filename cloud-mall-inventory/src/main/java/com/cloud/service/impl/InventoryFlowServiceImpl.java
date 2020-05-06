package com.cloud.service.impl;

import com.cloud.mapper.InventoryFlowMapper;
import com.cloud.model.inventory.InventoryFlow;
import com.cloud.service.InventoryFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryFlowServiceImpl implements InventoryFlowService {

    @Autowired
    InventoryFlowMapper inventoryFlowMapper;

    @Override
    public int save(InventoryFlow flow) {

        return this.inventoryFlowMapper.save(flow);
    }

    @Override
    public int update(InventoryFlow flow) {

        return this.inventoryFlowMapper.update(flow);
    }

    @Override
    public int delete(InventoryFlow flow) {
        return this.inventoryFlowMapper.delete(flow);
    }

    @Override
    public int deleteById(Long id) {

        return this.inventoryFlowMapper.deleteById(id);
    }

    @Override
    public InventoryFlow findById(Long id) {

        return this.inventoryFlowMapper.findById(id);
    }

    @Override
    public InventoryFlow findBySkuId(Long skuId) {

        return this.inventoryFlowMapper.findBySkuId(skuId);
    }
}
