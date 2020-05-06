package com.cloud.service;

import com.cloud.model.inventory.InventoryReminder;

public interface InventoryReminderService {

    public int save(InventoryReminder reminder);

    public int update(InventoryReminder reminder);

    public int delete(InventoryReminder reminder);

    public int deleteById(Long id);

    public InventoryReminder findById(Long id);
}
