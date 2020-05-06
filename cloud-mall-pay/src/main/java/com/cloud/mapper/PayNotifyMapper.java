package com.cloud.mapper;

import com.cloud.model.pay.PayNotify;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PayNotifyMapper {

    public int save(PayNotify payNotify);

    public int update(PayNotify payNotify);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public PayNotify findById(Long id);

    public List<PayNotify> findByOrderId(Long orderId);
}
