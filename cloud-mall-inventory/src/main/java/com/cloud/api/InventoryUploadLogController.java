package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.exception.InventoryBizException;
import com.cloud.model.inventory.InventoryUploadLog;
import com.cloud.service.InventoryUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/upload-log")
public class InventoryUploadLogController {

    @Autowired
    InventoryUploadLogService inventoryUploadLogService;

    @PostMapping("/add")
    public ResponseResult add(InventoryUploadLog inventoryUploadLog) {
        if (inventoryUploadLog == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getMessage());
        }

        int resultCode = this.inventoryUploadLogService.save(inventoryUploadLog);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_ADD_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "添加库存上传日志成功");
    }

    @GetMapping("/find-by-id")
    public ResponseResult getInventoryUploadLogById(@RequestParam("id") Long id){
        InventoryUploadLog inventoryUploadLog = this.inventoryUploadLogService.findById(id);
        if (inventoryUploadLog == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getMessage());
        }
        ResponseResult result = new ResponseResult();
        return result.success(inventoryUploadLog);
    }

    @PutMapping("/update")
    public ResponseResult update(InventoryUploadLog inventoryUploadLog) {
        if (inventoryUploadLog == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getMessage());
        }

        int resultCode = this.inventoryUploadLogService.update(inventoryUploadLog);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_UPDATE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_UPDATE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "更新库存上传日志成功");
    }

    @DeleteMapping("/delete")
    public ResponseResult delete(InventoryUploadLog inventoryUploadLog) {
        if (inventoryUploadLog == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_NULL.getMessage());
        }

        int resultCode = this.inventoryUploadLogService.delete(inventoryUploadLog);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存上传日志成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        int resultCode = this.inventoryUploadLogService.deleteById(id);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_UPLOAD_LOG_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_UPLOAD_LOG_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存上传日志成功");
    }
}
