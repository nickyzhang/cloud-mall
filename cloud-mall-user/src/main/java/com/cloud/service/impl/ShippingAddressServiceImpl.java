package com.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.mapper.ShippingAddressMapper;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.model.user.ShippingAddress;
import com.cloud.service.ShippingAddressService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired
    ShippingAddressMapper addressMapper;

    @Override
    public int save(ShippingAddress address) {
        return this.addressMapper.save(address);
    }

    @Override
    public int saveList(List<ShippingAddress> addressList) {

        List<ShippingAddress> addresses = new ArrayList<>();
        int count = 0;
        int code = 0;
        for (ShippingAddress address : addressList) {
            addresses.add(address);
            count++;
            if (count % 20 == 0) {
                code = this.addressMapper.saveList(addresses);
            }
            addresses.clear();
        }
        return code;
    }

    @Override
    public int update(ShippingAddress address) {
        return this.addressMapper.update(address);
    }

    @Override
    public int deleteShippingAddressByUserId(Long userId) {
        return this.addressMapper.deleteShippingAddressByUserId(userId);
    }

    @Override
    public int deleteShippingAddressByAddressId(Long addressId) {
        return this.addressMapper.deleteShippingAddressByAddressId(addressId);
    }

    @Override
    public ShippingAddress findShippingAddressByAddressId(Long addressId) {
        return this.addressMapper.findShippingAddressByAddressId(addressId);
    }

    @Override
    public List<ShippingAddress> findShippingAddressListByUserId(Long userId) {
        return this.addressMapper.findShippingAddressListByUserId(userId);
    }
}
