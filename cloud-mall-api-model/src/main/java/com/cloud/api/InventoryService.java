package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.inventory.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="cloud-mall-inventory",decode404 = true)
public interface InventoryService {
    @RequestMapping(value = "/catalog/inventory/findBySkuId",method = RequestMethod.GET)
    public ResponseResult getInventoryBySkuId(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/inventory/add",method = RequestMethod.POST)
    public ResponseResult add(@RequestBody  Inventory inventory);

    @RequestMapping(value = "/catalog/inventory/deduct/available-stock",method = RequestMethod.POST)
    public ResponseResult preOccupyStock(@RequestParam("skuId") Long skuId, @RequestParam("stockNum") Integer stockNum);

    @RequestMapping(value = "/catalog/inventory/deduct/total-stock",method = RequestMethod.POST)
    public ResponseResult deductTotalStock(@RequestParam("skuId") Long skuId, @RequestParam("stockNum") Integer stockNum);

    @RequestMapping(value = "/catalog/inventory/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody Inventory inventory);
}
