package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.exception.InventoryBizException;
import com.cloud.model.inventory.InventoryReminder;
import com.cloud.service.InventoryReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/reminder")
public class InventoryReminderController {

    @Autowired
    InventoryReminderService inventoryReminderService;

    @PostMapping("/add")
    public ResponseResult add(InventoryReminder inventoryReminder) {
        if (inventoryReminder == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_NULL.getMessage());
        }

        int resultCode = this.inventoryReminderService.save(inventoryReminder);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_ADD_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "添加库存警告提醒成功");
    }

    @GetMapping("/find-by-id")
    public ResponseResult getInventoryReminderById(@RequestParam("id") Long id){
        InventoryReminder InventoryReminder = this.inventoryReminderService.findById(id);
        if (InventoryReminder == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_NULL.getMessage());
        }
        ResponseResult result = new ResponseResult();
        return result.success(InventoryReminder);
    }

    @PutMapping("/update")
    public ResponseResult update(InventoryReminder inventoryReminder) {
        if (inventoryReminder == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_NULL.getMessage());
        }

        int resultCode = this.inventoryReminderService.update(inventoryReminder);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_UPDATE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_UPDATE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "更新库存警告提醒成功");
    }

    @DeleteMapping("/delete")
    public ResponseResult delete(InventoryReminder inventoryReminder) {
        if (inventoryReminder == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_NULL.getMessage());
        }

        int resultCode = this.inventoryReminderService.delete(inventoryReminder);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存警告提醒成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        int resultCode = this.inventoryReminderService.deleteById(id);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_REMINDER_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_REMINDER_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存警告提醒成功");
    }
}
