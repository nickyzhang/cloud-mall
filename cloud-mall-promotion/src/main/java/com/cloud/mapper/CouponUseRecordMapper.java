package com.cloud.mapper;

import com.cloud.model.promotion.CouponUseRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CouponUseRecordMapper {

    public int save(CouponUseRecord record);

    public int saveList(List<CouponUseRecord> recordList);

    public int update(CouponUseRecord record);

    public int delete(Long id);

    public int deleteUseRecordByUserId(Long userId);

    public int deleteUseRecordByCouponId(Long couponId);

    public CouponUseRecord find(Long id);

    public CouponUseRecord findUseRecordByCouponNo(String couponNo);

    public CouponUseRecord findUseRecordByCouponId(Long couponId);

    public List<CouponUseRecord> findUseRecordListByUserId(Long userId);

    public List<CouponUseRecord> findUseRecordListByOrderId(Long orderId);

    public List<CouponUseRecord> findUseRecordByOrderNo(String orderNo);

    public List<CouponUseRecord> findAll();

    public Long getUseNumByTemplateId(Long templateId);
}
