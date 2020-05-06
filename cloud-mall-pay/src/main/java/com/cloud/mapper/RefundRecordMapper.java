package com.cloud.mapper;

import com.cloud.model.pay.RefundRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RefundRecordMapper {
    
    public int save(RefundRecord payRecord);

    public int update(RefundRecord payRecord);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public int deleteByOrderNo(Long orderNo);

    public RefundRecord findByTradeNo(String tradeNo);

    public RefundRecord findById(Long id);

    public RefundRecord findByOrderId(Long orderId);

    public RefundRecord findByOrderNo(Long orderNo);

    public List<RefundRecord> findByBuyerId(Long buyerId);

    public Long count();
}
