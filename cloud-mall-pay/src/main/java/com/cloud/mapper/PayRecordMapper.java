package com.cloud.mapper;

import com.cloud.model.pay.PayRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PayRecordMapper {

    public int save(PayRecord payRecord);

    public int update(PayRecord payRecord);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public int deleteByOrderNo(Long orderNo);

    public PayRecord findByTradeNo(String tradeNo);

    public PayRecord findById(Long id);

    public PayRecord findByOrderId(Long orderId);

    public PayRecord findByOrderNo(Long orderNo);

    public List<PayRecord> findByBuyerId(Long buyerId);

    public Long count();
}
