package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayNotifyDTO;
import com.cloud.mapper.PayNotifyMapper;
import com.cloud.model.pay.PayNotify;
import com.cloud.service.PayNotifyService;
import com.cloud.vo.pay.PayNotifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayNotifyServiceImpl implements PayNotifyService {
    @Autowired
    PayNotifyMapper payNotifyMapper;

    @Override
    public int save(PayNotifyDTO payNotifyDTO) {
        PayNotify notify = BeanUtils.copy(payNotifyDTO,PayNotify.class);
        return this.payNotifyMapper.save(notify);
}

    @Override
    public int update(PayNotifyDTO payNotifyDTO) {
        PayNotify notify = BeanUtils.copy(payNotifyDTO,PayNotify.class);
        return this.payNotifyMapper.update(notify);
    }

    @Override
    public int deleteById(Long id) {
        return this.payNotifyMapper.deleteById(id);
    }

    @Override
    public int deleteByOrderId(Long orderId) {
        return this.payNotifyMapper.deleteByOrderId(orderId);
    }

    @Override
    public PayNotifyVO findById(Long id) {
        PayNotify notify = this.payNotifyMapper.findById(id);
        return BeanUtils.copy(notify, PayNotifyVO.class);
    }

    @Override
    public List<PayNotify> findByOrderId(Long orderId) {
        List<PayNotify> notifyList = this.payNotifyMapper.findByOrderId(orderId);
        return BeanUtils.copy(notifyList, PayNotify.class);
    }
}
