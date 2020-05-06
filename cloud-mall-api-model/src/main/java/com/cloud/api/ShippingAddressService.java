package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.user.ShippingAddress;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "/catalogcloud-mall-user")
public interface ShippingAddressService {

    @RequestMapping(value = "/user/address/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody ShippingAddress address);

    @RequestMapping(value = "/user/address/batchAdd",method = RequestMethod.POST)
    public ResponseResult saveList();


    @RequestMapping(value = "/user/address/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody ShippingAddress address);

    @RequestMapping(value = "/user/address/findShippingAddressByAddressId",method = RequestMethod.GET)
    public ResponseResult<ShippingAddress> findShippingAddressByAddressId(@RequestParam("addressId") Long addressId);

    @RequestMapping(value = "/user/address/findShippingAddressListByUserId",method = RequestMethod.GET)
    public ResponseResult<List<ShippingAddress>> findShippingAddressListByUserId(@RequestParam("userId") Long userId);
}
