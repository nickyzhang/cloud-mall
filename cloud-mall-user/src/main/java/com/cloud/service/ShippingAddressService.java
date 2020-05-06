package com.cloud.service;

import com.cloud.model.user.ShippingAddress;
import java.util.List;

public interface ShippingAddressService {
    public int save(ShippingAddress address);
    public int saveList(List<ShippingAddress> addressList);
    public int update(ShippingAddress address);
    public int deleteShippingAddressByUserId(Long userId);
    public int deleteShippingAddressByAddressId(Long addressId);
    public ShippingAddress findShippingAddressByAddressId(Long addressId);
    public List<ShippingAddress> findShippingAddressListByUserId(Long userId);
}
