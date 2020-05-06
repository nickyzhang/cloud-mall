package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.exception.InventoryBizException;
import com.cloud.model.inventory.InventoryFlow;
import com.cloud.service.InventoryFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/flow")
public class InventoryFlowController {

    @Autowired
    InventoryFlowService inventoryFlowService;

    @PostMapping("/add")
    public ResponseResult add(InventoryFlow inventoryFlow) {
        if (inventoryFlow == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_NULL.getMessage());
        }

        int resultCode = this.inventoryFlowService.save(inventoryFlow);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_ADD_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_ADD_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "添加库存流水成功");
    }

    @GetMapping("/findB-by-id")
    public ResponseResult getInventoryFlowById(@RequestParam("id") Long id){
        InventoryFlow inventoryFlow = this.inventoryFlowService.findById(id);
        if (inventoryFlow == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_NULL.getMessage());
        }
        ResponseResult result = new ResponseResult();
        return result.success(inventoryFlow);
    }

    @GetMapping("/find-by-skuid")
    public ResponseResult getInventoryFlowBySkuId(@RequestParam("skuId") Long skuId){
        InventoryFlow inventoryFlow = this.inventoryFlowService.findBySkuId(skuId);
        if (inventoryFlow == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_NULL.getMessage());
        }
        ResponseResult result = new ResponseResult();
        return result.success(inventoryFlow);
    }

    @PutMapping("/update")
    public ResponseResult update(InventoryFlow inventoryFlow) {
        if (inventoryFlow == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_NULL.getMessage());
        }

        int resultCode = this.inventoryFlowService.update(inventoryFlow);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_UPDATE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_UPDATE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "更新库存流水成功");
    }

    @DeleteMapping("/delete")
    public ResponseResult delete(InventoryFlow inventoryFlow) {
        if (inventoryFlow == null) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_NULL.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_NULL.getMessage());
        }

        int resultCode = this.inventoryFlowService.delete(inventoryFlow);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存流水成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id) {
        int resultCode = this.inventoryFlowService.deleteById(id);
        if (resultCode == 0) {
            throw new InventoryBizException(ResultCodeEnum.INVENTORY_FLOW_DELETE_FAILED.getCode(),
                    ResultCodeEnum.INVENTORY_FLOW_DELETE_FAILED.getMessage());
        }
        return new ResponseResult(ResultCodeEnum.OK.getCode(), "删除库存流水成功");
    }
}
