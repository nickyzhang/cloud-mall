package com.cloud.mapper;

import com.cloud.model.inventory.InventoryUploadLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryUploadLogMapper {

    public int save(InventoryUploadLog uploadLog);

    public int update(InventoryUploadLog uploadLog);

    public int delete(InventoryUploadLog uploadLog);

    public int deleteById(Long id);

    public InventoryUploadLog findById(Long id);
}
