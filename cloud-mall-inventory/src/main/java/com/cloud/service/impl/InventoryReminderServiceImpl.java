package com.cloud.service.impl;

import com.cloud.mapper.InventoryReminderMapper;
import com.cloud.model.inventory.InventoryReminder;
import com.cloud.service.InventoryReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryReminderServiceImpl implements InventoryReminderService{

    @Autowired
    InventoryReminderMapper inventoryReminderMapper;

    @Override
    public int save(InventoryReminder reminder) {
        return this.inventoryReminderMapper.save(reminder);
    }

    @Override
    public int update(InventoryReminder reminder) {
        return this.inventoryReminderMapper.update(reminder);
    }

    @Override
    public int delete(InventoryReminder reminder) {
        return this.inventoryReminderMapper.delete(reminder);
    }

    @Override
    public int deleteById(Long id) {
        return this.inventoryReminderMapper.deleteById(id);
    }

    @Override
    public InventoryReminder findById(Long id) {
        return this.inventoryReminderMapper.findById(id);
    }
}
