package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.CouponUseRecordDTO;
import com.cloud.exception.CouponUseRecordBizException;
import com.cloud.mapper.CouponTemplateMapper;
import com.cloud.mapper.CouponUseRecordMapper;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.model.promotion.CouponUseRecord;
import com.cloud.service.CouponUseRecordService;
import com.cloud.vo.promotion.CouponTemplateVO;
import com.cloud.vo.promotion.CouponUseRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CouponUseRecordServiceImpl implements CouponUseRecordService {

    @Autowired
    CouponUseRecordMapper couponUseRecordMapper;

    @Autowired
    CouponTemplateMapper couponTemplateMapper;

    @Override
    public int save(CouponUseRecordDTO recordDTO) {
        CouponTemplate template = couponTemplateMapper.find(recordDTO.getTemplateId());
        if (template == null) {
            throw new CouponUseRecordBizException("保存的优惠券使用记录对应的模板不存在");
        }
        // 这张优惠券不能到期。第一：到期主要指的是优惠券模板的到期时间，从此之后所有优惠券都将无效
        // 第二：用户领取优惠券之后，未在规定的时间内使用的优惠券，也属于到期，后面就无法消费
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 和模板的到期时间比较
        if (now.isAfter(template.getExpireTime())) {
            throw new CouponUseRecordBizException("优惠券模板已过期，无法使用");
        }

        // 和优惠券有效时间比较
        int days = template.getValidDays();
        LocalDateTime issueTime = template.getIssueTime();
        LocalDateTime expireDateTime = issueTime.plusDays(days);
        if (now.isAfter(expireDateTime)) {
            throw new CouponUseRecordBizException("优惠券时间已过期，无法使用");
        }

        // 我们还要判断使用优惠券是不是超过了优惠券模板定义的阀值
        if (recordDTO.getUsedAmount().compareTo(template.getAmountLimit()) > 0) {
            throw new CouponUseRecordBizException("优惠券使用的金额超过优惠券模板限制，无法使用");
        }

        // 开始保存优惠券使用记录
        CouponUseRecord record = BeanUtils.copy(recordDTO,CouponUseRecord.class);
        int code = this.couponUseRecordMapper.save(record);
        if (code == 0) {
            return code;
        }
        // 使用之后，我们需要更新优惠券模板的使用总金额和总数量
        template.setUsedNum(template.getUsedNum()+1);
        template.setUsedAmount(template.getUsedAmount().add(recordDTO.getUsedAmount()));
        return this.couponTemplateMapper.update(template);
    }

    @Override
    public int saveList(List<CouponUseRecordDTO> recordDTOList) {
        List<CouponUseRecord> couponUseRecordList = BeanUtils.copy(recordDTOList,CouponUseRecord.class);
        return this.couponUseRecordMapper.saveList(couponUseRecordList);
    }

    @Override
    public int update(CouponUseRecordDTO recordDTO) {
        if (recordDTO == null) {
            return 0;
        }
        CouponUseRecord record = BeanUtils.copy(recordDTO,CouponUseRecord.class);
        return this.couponUseRecordMapper.update(record);
    }

    @Override
    public int delete(Long id) {
        return this.couponUseRecordMapper.delete(id);
    }

    @Override
    public int deleteUseRecordByUserId(Long userId) {
        return this.couponUseRecordMapper.deleteUseRecordByUserId(userId);
    }

    @Override
    public int deleteUseRecordByCouponId(Long couponId) {
        return this.couponUseRecordMapper.deleteUseRecordByCouponId(couponId);
    }

    @Override
    public CouponUseRecordVO find(Long id) {
        CouponUseRecord record = this.couponUseRecordMapper.find(id);
        if (record == null) {
            return null;
        }
        return BeanUtils.copy(record,CouponUseRecordVO.class);
    }

    @Override
    public CouponUseRecordVO findUseRecordByCouponNo(String couponNo) {
        CouponUseRecord record = this.couponUseRecordMapper.findUseRecordByCouponNo(couponNo);
        if (record == null) {
            return null;
        }
        return BeanUtils.copy(record,CouponUseRecordVO.class);
    }

    @Override
    public CouponUseRecordVO findUseRecordByCouponId(Long couponId) {
        CouponUseRecord record = this.couponUseRecordMapper.findUseRecordByCouponId(couponId);
        if (record == null) {
            return null;
        }
        return BeanUtils.copy(record,CouponUseRecordVO.class);
    }

    @Override
    public List<CouponUseRecord> findUseRecordListByUserId(Long userId) {
        return this.couponUseRecordMapper.findUseRecordListByUserId(userId);
    }

    @Override
    public List<CouponUseRecord> findUseRecordListByOrderId(Long orderId) {
        return this.couponUseRecordMapper.findUseRecordListByOrderId(orderId);
    }

    @Override
    public List<CouponUseRecord> findUseRecordByOrderNo(String orderNo) {
        return this.couponUseRecordMapper.findUseRecordByOrderNo(orderNo);
    }

    @Override
    public List<CouponUseRecord> findAll() {
        return this.couponUseRecordMapper.findAll();
    }

    @Override
    public Long getUseNumByTemplateId(Long templateId){
        return this.couponUseRecordMapper.getUseNumByTemplateId(templateId);
    }
}
