package com.cloud.service;

import com.cloud.dto.promotion.CouponUseRecordDTO;
import com.cloud.model.promotion.CouponUseRecord;
import com.cloud.vo.promotion.CouponUseRecordVO;

import java.util.List;

public interface CouponUseRecordService {

    public int save(CouponUseRecordDTO recordDTO);

    public int saveList(List<CouponUseRecordDTO> recordDTOListList);

    public int update(CouponUseRecordDTO recordDTO);

    public int delete(Long id);

    public int deleteUseRecordByUserId(Long userId);

    public int deleteUseRecordByCouponId(Long couponId);

    public CouponUseRecordVO find(Long id);

    public CouponUseRecordVO findUseRecordByCouponNo(String couponNo);

    public CouponUseRecordVO findUseRecordByCouponId(Long couponId);

    public List<CouponUseRecord> findUseRecordListByUserId(Long userId);

    public List<CouponUseRecord> findUseRecordListByOrderId(Long orderId);

    public List<CouponUseRecord> findUseRecordByOrderNo(String orderNo);

    public List<CouponUseRecord> findAll();

    public Long getUseNumByTemplateId(Long templateId);
}
