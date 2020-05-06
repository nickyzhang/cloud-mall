package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayNotifyLogDTO;
import com.cloud.mapper.PayNotifyLogMapper;
import com.cloud.model.pay.PayNotifyLog;
import com.cloud.service.PayNotifyLogService;
import com.cloud.vo.pay.PayNotifyLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayNotifyLogServiceImpl implements PayNotifyLogService {

    @Autowired
    PayNotifyLogMapper payNotifyLogMapper;
    @Override
    public int save(PayNotifyLogDTO payNotifyLogDTO) {
        PayNotifyLog notifyLog = BeanUtils.copy(payNotifyLogDTO,PayNotifyLog.class);
        return this.payNotifyLogMapper.save(notifyLog);
    }

    @Override
    public int update(PayNotifyLogDTO payNotifyLogDTO) {
        PayNotifyLog notifyLog = BeanUtils.copy(payNotifyLogDTO,PayNotifyLog.class);
        return this.payNotifyLogMapper.update(notifyLog);
    }

    @Override
    public int deleteById(Long id) {
        return this.payNotifyLogMapper.deleteById(id);
    }

    @Override
    public int deleteByOrderId(Long orderId) {
        return this.payNotifyLogMapper.deleteByOrderId(orderId);
    }

    @Override
    public int deleteByNotifyId(Long notifyId) {
        return this.payNotifyLogMapper.deleteByNotifyId(notifyId);
    }

    @Override
    public PayNotifyLogVO findById(Long id) {
        PayNotifyLog notifyLog =  this.payNotifyLogMapper.findById(id);
        return BeanUtils.copy(notifyLog,PayNotifyLogVO.class);
    }

    @Override
    public List<PayNotifyLog> findByOrderId(Long orderId) {
        List<PayNotifyLog> notifyLogList = this.payNotifyLogMapper.findByOrderId(orderId);
        return BeanUtils.copy(notifyLogList, PayNotifyLog.class);
    }

    @Override
    public List<PayNotifyLog> findByNotifyId(Long notifyId) {
        List<PayNotifyLog> notifyLogList = this.payNotifyLogMapper.findByNotifyId(notifyId);
        return BeanUtils.copy(notifyLogList, PayNotifyLog.class);
    }
}
