package com.cloud.service.impl;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayRecordDTO;
import com.cloud.mapper.PayRecordMapper;
import com.cloud.model.pay.PayRecord;
import com.cloud.service.PayRecordService;
import com.cloud.vo.pay.PayRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayRecordServiceImpl implements PayRecordService {

    @Autowired
    PayRecordMapper payRecordMapper;

    @Override
    public int save(PayRecordDTO payRecordDTO) {
        PayRecord record = BeanUtils.copy(payRecordDTO,PayRecord.class);
        return this.payRecordMapper.save(record);
    }

    @Override
    public int update(PayRecordDTO payRecordDTO) {
        PayRecord record = BeanUtils.copy(payRecordDTO,PayRecord.class);
        return this.payRecordMapper.update(record);
    }

    @Override
    public int deleteById(Long tradeId) {
        return this.payRecordMapper.deleteById(tradeId);
    }

    @Override
    public int deleteByOrderId(Long orderId) {
        return this.payRecordMapper.deleteByOrderId(orderId);
    }

    @Override
    public int deleteByOrderNo(Long orderNo) {
        return this.payRecordMapper.deleteByOrderNo(orderNo);
    }

    @Override
    public PayRecordVO findByTradeNo(String tradeNo) {
        PayRecord payRecord = this.payRecordMapper.findByTradeNo(tradeNo);
        return BeanUtils.copy(payRecord,PayRecordVO.class);
    }

    @Override
    public PayRecordVO findById(Long id) {
        PayRecord payRecord = this.payRecordMapper.findById(id);
        return BeanUtils.copy(payRecord,PayRecordVO.class);
    }

    @Override
    public PayRecordVO findByOrderId(Long orderId) {
        PayRecord payRecord = this.payRecordMapper.findByOrderId(orderId);
        return BeanUtils.copy(payRecord,PayRecordVO.class);
    }

    @Override
    public PayRecordVO findByOrderNo(Long orderNo) {
        PayRecord payRecord = this.payRecordMapper.findByOrderNo(orderNo);
        return BeanUtils.copy(payRecord,PayRecordVO.class);
    }

    @Override
    public List<PayRecord> findByBuyerId(Long buyerId) {
        return this.payRecordMapper.findByBuyerId(buyerId);
    }
}
