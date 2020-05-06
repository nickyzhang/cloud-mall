package com.cloud.service;

import com.cloud.model.inventory.InventoryUploadLog;

public interface InventoryUploadLogService {

    public int save(InventoryUploadLog uploadLog);

    public int update(InventoryUploadLog uploadLog);

    public int delete(InventoryUploadLog uploadLog);

    public int deleteById(Long id);

    public InventoryUploadLog findById(Long id);
}
