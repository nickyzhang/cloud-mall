package com.cloud.service;

import com.cloud.dto.pay.PayNotifyLogDTO;
import com.cloud.model.pay.PayNotifyLog;
import com.cloud.vo.pay.PayNotifyLogVO;

import java.util.List;

public interface PayNotifyLogService {

    public int save(PayNotifyLogDTO payNotifyLogDTO);

    public int update(PayNotifyLogDTO payNotifyLogDTO);

    public int deleteById(Long id);

    public int deleteByOrderId(Long orderId);

    public int deleteByNotifyId(Long notifyId);

    public PayNotifyLogVO findById(Long id);

    public List<PayNotifyLog> findByOrderId(Long orderId);

    public List<PayNotifyLog> findByNotifyId(Long notifyId);
}
