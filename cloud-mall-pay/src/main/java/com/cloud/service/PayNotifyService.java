package com.cloud.service;

import com.cloud.dto.pay.PayNotifyDTO;
import com.cloud.model.pay.PayNotify;
import com.cloud.vo.pay.PayNotifyVO;
import java.util.List;

public interface PayNotifyService {

    public int save(PayNotifyDTO payNotifyDTO);

    public int update(PayNotifyDTO payNotifyDTO);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public PayNotifyVO findById(Long id);

    public List<PayNotify> findByOrderId(Long orderId);
}
