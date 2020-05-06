package com.cloud.mapper;

import com.cloud.model.inventory.InventoryReminder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryReminderMapper {

    public int save(InventoryReminder reminder);

    public int update(InventoryReminder reminder);

    public int delete(InventoryReminder reminder);

    public int deleteById(Long id);

    public InventoryReminder findById(Long id);
}
