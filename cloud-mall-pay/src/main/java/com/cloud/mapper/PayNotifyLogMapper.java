package com.cloud.mapper;

import com.cloud.model.pay.PayNotifyLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PayNotifyLogMapper {

    public int save(PayNotifyLog payNotifyLog);

    public int update(PayNotifyLog payNotifyLog);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public int deleteByNotifyId(Long notifyId);

    public PayNotifyLog findById(Long id);

    public List<PayNotifyLog> findByOrderId(Long orderId);

    public List<PayNotifyLog> findByNotifyId(Long notifyId);
}
