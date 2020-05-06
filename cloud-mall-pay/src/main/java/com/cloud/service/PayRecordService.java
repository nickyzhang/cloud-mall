package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.pay.PayRecordDTO;
import com.cloud.model.pay.PayRecord;
import com.cloud.vo.pay.PayRecordVO;
import java.util.List;

public interface PayRecordService {

    public int save(PayRecordDTO payRecordDTO);

    public int update(PayRecordDTO payRecordDTO);

    public int deleteById(Long tradeId);

    public int deleteByOrderId(Long orderId);

    public int deleteByOrderNo(Long orderNo);

    public PayRecordVO findByTradeNo(String tradeNo);

    public PayRecordVO findById(Long id);

    public PayRecordVO findByOrderId(Long orderId);

    public PayRecordVO findByOrderNo(Long orderNo);

    public List<PayRecord> findByBuyerId(Long buyerId);
}
