package com.cloud.service;

import com.cloud.dto.pay.RefundRecordDTO;
import com.cloud.model.pay.RefundRecord;
import com.cloud.vo.pay.RefundRecordVO;

import java.util.List;

public interface RefundRecordService {

    public int save(RefundRecordDTO refundRecordDTO);

    public int update(RefundRecordDTO refundRecordDTO);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public int deleteByOrderNo(Long orderNo);

    public RefundRecordVO findByTradeNo(String tradeNo);

    public RefundRecordVO findById(Long id);

    public RefundRecordVO findByOrderId(Long orderId);

    public RefundRecordVO findByOrderNo(Long orderNo);

    public List<RefundRecord> findByBuyerId(Long buyerId);

}
