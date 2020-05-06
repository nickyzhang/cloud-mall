package com.cloud.service.impl;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.CouponDTO;
import com.cloud.mapper.*;
import com.cloud.model.order.Order;
import com.cloud.model.promotion.*;
import com.cloud.service.CouponService;
import com.cloud.vo.promotion.CouponVO;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponTemplateMapper couponTemplateMapper;

    @Autowired
    CouponUseRecordMapper couponUseRecordMapper;

    @Autowired
    GenIdService genIdService;

    @Override
    public ResponseResult save(CouponDTO couponDTO) {
        ResponseResult result = new ResponseResult();
        Coupon coupon = BeanUtils.copy(couponDTO,Coupon.class);

        // 增加一张优惠券，就需要扣减可用的优惠券数量
        CouponTemplate template = this.couponTemplateMapper.find(couponDTO.getTemplateId());

        // 校验是否当前应该继续发放优惠券
        // 如果优惠券模板数量生成优惠券达到限定数量，则不能继续生成
        // 主要是针对满额减或者直减额。特征: 明确知道领取一张额度是多少，所以只要不超过发型数量即可
        if (template.getReceiveNum().equals(template.getIssueNum())) {
            return result.failed("优惠券数量已经达到发行数量");
        }

        // 对用户领取资格进行校验
        // 获取用户领取优惠券的次数限制
        int receiveNumLimit = template.getReceiveNumLimit();
        // 如果有次数限制，则进行校验；如果没有次数限制则放行
        if (receiveNumLimit > 0) {
            Long userId = couponDTO.getUserId();
            // 获取用户获取这个优惠券模板下优惠券数量，如果大于0说明已经领取了
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("templateId",template.getTemplateId());
            int userReceivedNum = couponMapper.getCouponNumByUserAndTemplate(map);
            // 如果用户领取数量已经超过了限制，则不允许再次领取优惠券了，丧失领取资格
            if (receiveNumLimit <= userReceivedNum ) {
                return result.failed(String.format("用户:[%s] 领取的优惠券数量已达到指定数量"));
            }
        }

        // 优惠券优惠的金额，也不能超过指定的金额，但是因为金额无法确定，所以不能在这里进行校验
        // 需要在使用的时候进行校验
        // 一般针对的是直折和满额折。特征：需要控制优惠额度，避免优惠力度过大，造成损失

        // 优惠券创建，说明已经领取,需要增加优惠券领取数量,更新优惠券模板
        template.setReceiveNum(template.getReceiveNum() + 1);
        this.couponTemplateMapper.update(template);
        coupon.setTemplate(template);
        int code = this.couponMapper.save(coupon);
        if (code == 0) {
            return result.failed("保存优惠券失败");
        }

        return code == 0 ? result.failed("保存优惠券失败") : result.success("保存优惠券成功",null);

    }

    @Override
    public ResponseResult saveList(List<Coupon> couponList) {
        ResponseResult result = new ResponseResult();
        int code = this.couponMapper.saveList(couponList);
        if (code == 0) {
            return result.failed("批量添加优惠券失败");
        }
        // 批量添加之后，更新优惠券模板接收数量
        int receiveNum = couponList.size();
        CouponTemplate template = couponList.get(0).getTemplate();
        if (receiveNum > template.getIssueNum()) {
            return result.failed("优惠券领取张数超过发行数量,请检查");
        }
        template.setReceiveNum(Long.valueOf(receiveNum));
        code = this.couponTemplateMapper.update(template);
        return code == 0 ? result.failed("批量保存优惠券失败") : result.success("批量保存优惠券成功",null);
    }

    @Override
    public ResponseResult update(CouponDTO couponDTO) {
        ResponseResult result = new ResponseResult();
        Coupon coupon = BeanUtils.copy(couponDTO,Coupon.class);
        int code = this.couponMapper.update(coupon);
        return code == 0 ? result.failed("更新优惠券失败") : result.success("更新优惠券成功",null);
    }

    @Override
    public ResponseResult delete(Long couponId) {
        int code=  this.couponMapper.delete(couponId);
        ResponseResult result = new ResponseResult();
        return code == 0 ? result.failed("删除优惠券失败") : result.success("删除优惠券成功",null);
    }

    @Override
    public ResponseResult batchDelete(List<Coupon> couponList) {
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(couponList)) {
            return result.failed("要使用的优惠券信息为空");
        }

        List<Long> idList = new ArrayList<>();
        // 对待使用的优惠券进行到期校验
        for (Coupon coupon : couponList) {
            if (!checkCoupon(coupon)) {
                return result.failed("有优惠券到期");
            }
            idList.add(coupon.getCouponId());
        }
        // 校验成功，则开始预扣优惠券
        int code =  this.couponMapper.batchDelete(idList);
        return code == 0 ? result.failed("批量删除优惠券失败") : result.success("批量删除优惠券成功",null);
    }

    /**
     * 对优惠券进行到期校验
     * @param coupon
     * @return
     */
    private boolean checkCoupon(Coupon coupon) {
        if (coupon == null) {
            return Boolean.FALSE;
        }

        CouponTemplate template = coupon.getTemplate();
        if (template == null) {
            return Boolean.FALSE;
        }

        LocalDateTime templateExpireTime = template.getExpireTime();
        if (LocalDateTime.now().isBefore(templateExpireTime)) {
            return Boolean.TRUE;
        }

        LocalDateTime couponExpiredTime = coupon.getExpiredTime();
        return LocalDateTime.now().isBefore(couponExpiredTime);
    }

    @Override
    public ResponseResult deleteCouponByTemplateId(Long templateId) {
        int code = this.couponMapper.deleteCouponByTemplateId(templateId);
        ResponseResult result = new ResponseResult();
        return code == 0 ? result.failed("删除保存优惠券失败") : result.success("删除优惠券成功",null);
    }

    @Override
    public ResponseResult deleteUnavailableCouponList() {
        int code =  this.couponMapper.deleteUnavailableCouponList();
        ResponseResult result = new ResponseResult();
        return code == 0 ? result.failed("删除所有不可用优惠券失败") : result.success("删除所有不可用优惠券成功",null);
    }

    @Override
    public ResponseResult deleteUnavailableCouponListByUserId(Long userId) {
        int code = this.couponMapper.deleteUnavailableCouponListByUserId(userId);
        ResponseResult result = new ResponseResult();
        return code == 0 ? result.failed("删除用户不可用优惠券失败") : result.success("删除用户不可用优惠券成功",null);
    }

    @Override
    public boolean isExpired(Long couponId) {
        CouponVO couponVO = find(couponId);
        return LocalDateTime.now().isAfter(couponVO.getExpiredTime());
    }

    @Override
    public CouponVO find(Long couponId) {
        Coupon coupon = this.couponMapper.find(couponId);
        if (coupon == null) {
            return null;
        }

        return BeanUtils.copy(coupon,CouponVO.class);
    }

    @Override
    public List<Coupon> findAll() {
        return this.couponMapper.findAll();
    }


    @Override
    public List<Coupon> findCouponListByUserId(Long userId) {
        return this.couponMapper.findCouponListByUserId(userId);
    }

    @Override
    public List<Coupon> findUnavailableCouponListByUserId(Long userId) {
        return this.couponMapper.findUnavailableCouponListByUserId(userId);
    }

    @Override
    public ResponseResult useCoupon(List<Coupon> couponList,Order order) {
        ResponseResult result = new ResponseResult();
//        if (CollectionUtils.isEmpty(couponList)) {
//            return result.failed("要使用的优惠券列表为空");
//        }
//        List<CouponUseRecord> couponUseRecordList = new ArrayList<>();
//        CouponUseRecord record = new CouponUseRecord();
//        for (Coupon coupon : couponList) {
//            record = new CouponUseRecord();
//            record.setId(this.genIdService.genId());
//            record.setCouponId(coupon.getCouponId());
//            record.setCouponNo(coupon.getCouponNo());
//            record.set
//        }
//        this.couponUseRecordMapper.sa
        return result;
    }

    @Override
    public ResponseResult preUseCoupon(List<Coupon> couponList) {
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(couponList)) {
            return result.failed("要预扣的优惠券为空");
        }

        // 对待使用的优惠券进行到期校验
        for (Coupon coupon : couponList) {
            if (!checkCoupon(coupon)) {
                return result.failed(String.format("优惠券%d到期",coupon.getCouponId()));
            }
        }
        // 校验成功，则开始预扣优惠券
        int code = 0;
        for (Coupon coupon : couponList) {
            coupon.setDeleted(true);
            coupon.setUpdateTime(LocalDateTime.now());
            code = this.couponMapper.update(coupon);
        }

        return code == 0 ? result.failed("预扣优惠券失败") : result.success("预扣优惠券成功",null);
    }

}
