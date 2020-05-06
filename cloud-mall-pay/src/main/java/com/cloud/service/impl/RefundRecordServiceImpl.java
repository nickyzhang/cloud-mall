package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.RefundRecordDTO;
import com.cloud.mapper.RefundRecordMapper;
import com.cloud.model.pay.RefundRecord;
import com.cloud.service.RefundRecordService;
import com.cloud.vo.pay.RefundRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RefundRecordServiceImpl implements RefundRecordService{

    @Autowired
    RefundRecordMapper refundRecordMapper;

    @Override
    public int save(RefundRecordDTO refundRecordDTO) {
        RefundRecord refundRecord = BeanUtils.copy(refundRecordDTO,RefundRecord.class);
        return this.refundRecordMapper.save(refundRecord);
    }

    @Override
    public int update(RefundRecordDTO refundRecordDTO) {
        RefundRecord refundRecord = BeanUtils.copy(refundRecordDTO,RefundRecord.class);
        return this.refundRecordMapper.update(refundRecord);
    }

    @Override
    public int deleteById(Long id) {
        return this.refundRecordMapper.deleteById(id);
    }

    @Override
    public int deleteByOrderId(Long orderId) {
        return this.refundRecordMapper.deleteByOrderId(orderId);
    }

    @Override
    public int deleteByOrderNo(Long orderNo) {
        return this.refundRecordMapper.deleteByOrderNo(orderNo);
    }

    @Override
    public RefundRecordVO findByTradeNo(String tradeNo) {
        RefundRecord refundRecord = this.refundRecordMapper.findByTradeNo(tradeNo);
        return BeanUtils.copy(refundRecord,RefundRecordVO.class);
    }

    @Override
    public RefundRecordVO findById(Long id) {
        RefundRecord refundRecord = this.refundRecordMapper.findById(id);
        return BeanUtils.copy(refundRecord,RefundRecordVO.class);
    }

    @Override
    public RefundRecordVO findByOrderId(Long orderId) {
        RefundRecord refundRecord = this.refundRecordMapper.findByOrderId(orderId);
        return BeanUtils.copy(refundRecord,RefundRecordVO.class);
    }

    @Override
    public RefundRecordVO findByOrderNo(Long orderNo) {
        RefundRecord refundRecord = this.refundRecordMapper.findByOrderNo(orderNo);
        return BeanUtils.copy(refundRecord,RefundRecordVO.class);
    }

    @Override
    public List<RefundRecord> findByBuyerId(Long buyerId) {
        return this.refundRecordMapper.findByBuyerId(buyerId);
    }
}
