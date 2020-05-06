package com.cloud.service.impl;

import com.cloud.mapper.InventoryUploadLogMapper;
import com.cloud.model.inventory.InventoryUploadLog;
import com.cloud.service.InventoryUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryUploadLogServiceImpl implements InventoryUploadLogService{

    @Autowired
    InventoryUploadLogMapper inventoryUploadLogMapper;

    @Override
    public int save(InventoryUploadLog uploadLog) {

        return this.inventoryUploadLogMapper.save(uploadLog);
    }

    @Override
    public int update(InventoryUploadLog uploadLog) {

        return this.inventoryUploadLogMapper.update(uploadLog);
    }

    @Override
    public int delete(InventoryUploadLog uploadLog) {

        return this.inventoryUploadLogMapper.delete(uploadLog);
    }

    @Override
    public int deleteById(Long id) {

        return this.inventoryUploadLogMapper.deleteById(id);
    }

    @Override
    public InventoryUploadLog findById(Long id) {

        return this.inventoryUploadLogMapper.findById(id);
    }
}
