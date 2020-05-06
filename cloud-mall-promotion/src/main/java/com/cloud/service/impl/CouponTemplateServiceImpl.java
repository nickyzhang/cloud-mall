package com.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.dto.promotion.CouponTemplateDTO;
import com.cloud.enums.LaunchMethod;
import com.cloud.enums.TemplateStatus;
import com.cloud.exception.CouponTemplateBizException;
import com.cloud.mapper.CouponTemplateBrandMapper;
import com.cloud.mapper.CouponTemplateCatMapper;
import com.cloud.mapper.CouponTemplateMapper;
import com.cloud.mapper.CouponTemplateSkuMapper;
import com.cloud.message.CouponDistributionMessage;
import com.cloud.model.promotion.*;
import com.cloud.mq.rabbitmq.service.RabbitSender;
import com.cloud.service.CouponTemplateService;
import com.cloud.vo.promotion.CouponTemplateVO;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional
public class CouponTemplateServiceImpl implements CouponTemplateService{

    @Autowired
    CouponTemplateMapper couponTemplateMapper;

    @Autowired
    CouponTemplateBrandMapper couponTemplateBrandMapper;

    @Autowired
    CouponTemplateCatMapper couponTemplateCatMapper;

    @Autowired
    CouponTemplateSkuMapper couponTemplateSkuMapper;

    @Autowired
    RabbitSender rabbitSender;

    @Override
    public int save(CouponTemplateDTO templateDTO) {
        CouponTemplate template = BeanUtils.copy(templateDTO,CouponTemplate.class);
        int code = this.couponTemplateMapper.save(template);
        if (code == 0) {
            return 0;
        }

        List<Long> skuList = templateDTO.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            CouponTemplateSku couponTemplateSku = null;
            for (Long skuId : skuList) {
                couponTemplateSku = new CouponTemplateSku();
                couponTemplateSku.setSkuId(skuId);
                couponTemplateSku.setTemplateId(templateDTO.getTemplateId());
                couponTemplateSku.setDeleted(false);
                couponTemplateSku.setCreateTime(LocalDateTime.now());
                couponTemplateSku.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateSkuMapper.saveCouponTemplateSku(couponTemplateSku);
                if (code == 0) {
                    return 0;
                }
            }
        }
        List<Long> catList = templateDTO.getCatList();
        if (CollectionUtils.isNotEmpty(catList)) {
            CouponTemplateCat couponTemplateCat = null;
            for (Long catId : catList) {
                couponTemplateCat = new CouponTemplateCat();
                couponTemplateCat.setCatId(catId);
                couponTemplateCat.setTemplateId(template.getTemplateId());
                couponTemplateCat.setDeleted(false);
                couponTemplateCat.setCreateTime(LocalDateTime.now());
                couponTemplateCat.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateCatMapper.saveCouponTemplateCat(couponTemplateCat);
                if (code == 0) {
                    return 0;
                }
            }
        }

        List<Long> brandList = templateDTO.getBrandList();
        if (CollectionUtils.isNotEmpty(brandList)) {
            CouponTemplateBrand couponTemplateBrand = null;
            for (Long brandId : brandList) {
                couponTemplateBrand = new CouponTemplateBrand();
                couponTemplateBrand.setTemplateId(template.getTemplateId());
                couponTemplateBrand.setDeleted(false);
                couponTemplateBrand.setCreateTime(LocalDateTime.now());
                couponTemplateBrand.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateBrandMapper.saveCouponTemplateBrand(couponTemplateBrand);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    @Override
    public int saveList(List<CouponTemplate> templateList) {
        return this.couponTemplateMapper.saveList(templateList);
    }

    @Override
    public int update(CouponTemplateDTO templateDTO) {
        CouponTemplate template = BeanUtils.copy(templateDTO,CouponTemplate.class);
        int code = this.couponTemplateMapper.update(template);
        if (code == 0) {
            log.error("[CouponTemplateService -> update()] 更新失败: "+ JSONUtils.objectToJson(template));
            return code;
        }

        List<CouponTemplateCat> couponTemplateCatList = this.couponTemplateCatMapper.findByTemplateId(template.getTemplateId());
        if (CollectionUtils.isNotEmpty(couponTemplateCatList)) {
            Set<Long> oldCatSet = new HashSet<>();
            for (CouponTemplateCat couponTemplateCat : couponTemplateCatList) {
                oldCatSet.add(couponTemplateCat.getCatId());
            }

            Set<Long> newCatSet = new HashSet<Long>(templateDTO.getCatList());

            Set<Long> deleteCatSet = Sets.difference(oldCatSet,newCatSet);
            log.info("[CouponTemplateService -> update()] deleteCatSet:"+deleteCatSet);
            for (Long deleteCatId : deleteCatSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("couponId",template.getTemplateId());
                map.put("catId",deleteCatId);
                code = this.couponTemplateCatMapper.deleteCouponTemplateCat(map);
                if (code == 0) {
                    log.error("[CouponTemplateService -> update()] deleteCouponTemplateCat失败");
                    return code;
                }
            }

            Set<Long> addCatSet = Sets.difference(newCatSet,oldCatSet);
            log.info("[CouponTemplateService -> update()] addCatSet:"+addCatSet);
            for (Long addCatId : addCatSet) {
                CouponTemplateCat couponTemplateCat = new CouponTemplateCat();
                couponTemplateCat.setCatId(addCatId);
                couponTemplateCat.setTemplateId(template.getTemplateId());
                couponTemplateCat.setDeleted(false);
                couponTemplateCat.setCreateTime(LocalDateTime.now());
                couponTemplateCat.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateCatMapper.saveCouponTemplateCat(couponTemplateCat);
                if (code == 0) {
                    log.error("[CouponTemplateService -> update()] saveCouponTemplateCat失败");
                    return 0;
                }
            }
        }

        List<CouponTemplateBrand> couponTemplateBrandList = this.couponTemplateBrandMapper.findByTemplateId(template.getTemplateId());
        if (CollectionUtils.isNotEmpty(couponTemplateBrandList)) {
            Set<Long> oldBrandSet = new HashSet<>();
            for (CouponTemplateBrand couponTemplateBrand : couponTemplateBrandList) {
                oldBrandSet.add(couponTemplateBrand.getBrandId());
            }

            Set<Long> newBrandSet = new HashSet<>(templateDTO.getBrandList());

            Set<Long> deleteBrandSet = Sets.difference(oldBrandSet,newBrandSet);
            log.info("[CouponTemplateService -> update()] deleteBrandSet:"+deleteBrandSet);
            for (Long deleteBrandId : deleteBrandSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("couponId",template.getTemplateId());
                map.put("brandId",deleteBrandId);
                code = this.couponTemplateBrandMapper.deleteCouponTemplateBrand(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addBrandSet = Sets.difference(newBrandSet,oldBrandSet);
            log.info("[CouponTemplateService -> update()] addBrandSet:"+addBrandSet);
            for (Long addBrandId : addBrandSet) {
                CouponTemplateBrand couponTemplateBrand = new CouponTemplateBrand();
                couponTemplateBrand.setBrandId(addBrandId);
                couponTemplateBrand.setTemplateId(template.getTemplateId());
                couponTemplateBrand.setDeleted(false);
                couponTemplateBrand.setCreateTime(LocalDateTime.now());
                couponTemplateBrand.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateBrandMapper.saveCouponTemplateBrand(couponTemplateBrand);
                if (code == 0) {
                    return 0;
                }
            }
        }

        List<CouponTemplateSku> couponTemplateSkuList = this.couponTemplateSkuMapper.findByTemplateId(template.getTemplateId());
        if (CollectionUtils.isNotEmpty(couponTemplateSkuList)) {
            Set<Long> oldSkuSet = new HashSet<>();
            for (CouponTemplateSku couponTemplateSku : couponTemplateSkuList) {
                oldSkuSet.add(couponTemplateSku.getSkuId());
            }

            Set<Long> newSkuSet = new HashSet<>(templateDTO.getSkuList());

            Set<Long> deleteSkuSet = Sets.difference(oldSkuSet,newSkuSet);
            log.info("[CouponTemplateService -> update()] deleteSkuSet:"+deleteSkuSet);
            for (Long deleteSkuId : deleteSkuSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("couponId",template.getTemplateId());
                map.put("skuId",deleteSkuId);
                code = this.couponTemplateSkuMapper.deleteCouponTemplateSku(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addSkuSet = Sets.difference(newSkuSet,oldSkuSet);
            log.info("[CouponTemplateService -> update()] addSkuSet:"+addSkuSet);
            for (Long addSkuId : addSkuSet) {
                CouponTemplateSku couponTemplateSku = new CouponTemplateSku();
                couponTemplateSku.setSkuId(addSkuId);
                couponTemplateSku.setTemplateId(template.getTemplateId());
                couponTemplateSku.setDeleted(false);
                couponTemplateSku.setCreateTime(LocalDateTime.now());
                couponTemplateSku.setUpdateTime(LocalDateTime.now());
                code = this.couponTemplateSkuMapper.saveCouponTemplateSku(couponTemplateSku);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    private void doIssueCoupon(CouponTemplateDTO couponTemplateDTO) throws Exception {
        // 优惠券模板已经创建好了，状态处于新建或者发行之前的，后面的构建优惠券的操作不要进行了
        // 如果优惠券模板分发方式是只能由用户自己领取，则直接返回；
        // 否则如果是系统直接发送到用户，则需要异步生成优惠券;然后发送指定类型的用户，并且通知用户
        Integer launchMethod = couponTemplateDTO.getLaunchMethod();
        if (launchMethod == LaunchMethod.ONLY_RECEIVE.getCode()) {
            return;
        }
        CouponTemplate template = BeanUtils.copy(couponTemplateDTO,CouponTemplate.class);
        // 将消息发送给消息中间件，由消费者异步分发优惠券
        Integer memberType = couponTemplateDTO.getMemberType();
        CouponDistributionMessage message = new CouponDistributionMessage(memberType, template);
        log.info(JSONObject.toJSONString(message));
        this.rabbitSender.send("coupon.build","coupon.build",message);
        log.info("发送成功");
    }

    @Override
    public int issueCoupon(Long templateId) {
        CouponTemplateVO couponTemplateVO = this.find(templateId);
        if (couponTemplateVO == null) {
            throw new CouponTemplateBizException("不存在这个模板: "+templateId);
        }

        CouponTemplateDTO couponTemplateDTO = BeanUtils.copy(couponTemplateVO,CouponTemplateDTO.class);
        // 更改状态为发行中
        couponTemplateDTO.setTemplateStatus(TemplateStatus.LAUNCHING.getCode());
        couponTemplateDTO.setUpdateTime(LocalDateTime.now());
        log.info("更改优惠券状态为发行中");
        log.info(JSONObject.toJSONString(couponTemplateDTO));
        int code = this.update(couponTemplateDTO);
        if (code == 0) {
            throw new CouponTemplateBizException("更新优惠券模板状态失败");
        }

//        log.info("优惠券模板状态:"+couponTemplateVO.getTemplateStatus()+" 然而:"+TemplateStatus.AUDITED.getCode());
//        log.info("判断结果"+couponTemplateDTO.getTemplateStatus().equals(TemplateStatus.AUDITED.getCode()));
//        if (!(couponTemplateDTO.getTemplateStatus().intValue() == TemplateStatus.AUDITED.getCode())) {
//            throw new CouponTemplateBizException("优惠券模板状态不是已审核，不能发行优惠券");
//        }

        try {
            this.doIssueCoupon(couponTemplateDTO);
        } catch (Exception e) {
            throw new CouponTemplateBizException(e.getMessage());
        }
        // 优惠券发行完毕，更新状态为已发放
        couponTemplateDTO.setTemplateStatus(TemplateStatus.LAUNCHED.getCode());
        LocalDateTime now = LocalDateTime.now();
        couponTemplateDTO.setIssueTime(now);
        couponTemplateDTO.setUpdateTime(now);
        log.info("更改优惠券状态为已发行，并且设置发行时间");
        log.info(JSONObject.toJSONString(couponTemplateDTO));
        return this.update(couponTemplateDTO);
    }

    @Override
    public List<CouponTemplate> findTemplateListBySkuIds(Long[] skuIds) {
        if (ArrayUtils.isEmpty(skuIds)) {
            return null;
        }
        return this.couponTemplateMapper.findTemplateListBySkuIds(skuIds);
    }

    @Override
    public CouponTemplateVO findTemplateByCouponId(Long couponId) {
        CouponTemplate template = this.couponTemplateMapper.findTemplateByCouponId(couponId);
        if (template == null) {
            return null;
        }

        CouponTemplateVO couponTemplateVO = BeanUtils.copy(template,CouponTemplateVO.class);
        List<CouponTemplateCat> couponTemplateCatList = this.couponTemplateCatMapper.
                findByTemplateId(template.getTemplateId());
        List<Long> catList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(couponTemplateCatList)){
            couponTemplateCatList.forEach(elements -> {
                catList.add(elements.getCatId());
            });
            couponTemplateVO.setCatList(catList);
        }

        List<CouponTemplateBrand> couponTemplateBrandList = this.couponTemplateBrandMapper.
                findByTemplateId(template.getTemplateId());
        List<Long> brandList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(brandList)){
            couponTemplateBrandList.forEach(elements -> {
                brandList.add(elements.getBrandId());
            });
            couponTemplateVO.setBrandList(brandList);
        }

        List<CouponTemplateSku> couponTemplateSkuList = this.couponTemplateSkuMapper.
                findByTemplateId(template.getTemplateId());
        List<Long> skuList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(couponTemplateSkuList)){
            couponTemplateSkuList.forEach(elements -> {
                skuList.add(elements.getSkuId());
            });
            couponTemplateVO.setSkuList(skuList);
        }
        return couponTemplateVO;
    }

    @Override
    public int delete(Long templateId) {
        return this.couponTemplateMapper.delete(templateId);
    }

    @Override
    public int deleteAllExpiredTemplateList() {
        return this.couponTemplateMapper.deleteAllExpiredTemplateList();
    }

    @Override
    public CouponTemplateVO find(Long templateId) {
        CouponTemplate template = this.couponTemplateMapper.find(templateId);
        if (template == null) {
            return null;
        }

        CouponTemplateVO couponTemplateVO = BeanUtils.copy(template,CouponTemplateVO.class);
        List<CouponTemplateCat> couponTemplateCatList = this.couponTemplateCatMapper.findByTemplateId(templateId);
        List<Long> catList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(couponTemplateCatList)){
            couponTemplateCatList.forEach(elements -> {
                catList.add(elements.getCatId());
            });
            couponTemplateVO.setCatList(catList);
        }

        List<CouponTemplateBrand> couponTemplateBrandList = this.couponTemplateBrandMapper.findByTemplateId(templateId);
        List<Long> brandList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(brandList)){
            couponTemplateBrandList.forEach(elements -> {
                brandList.add(elements.getBrandId());
            });
            couponTemplateVO.setBrandList(brandList);
        }

        List<CouponTemplateSku> couponTemplateSkuList = this.couponTemplateSkuMapper.findByTemplateId(templateId);
        List<Long> skuList = new ArrayList<>(11);
        if (CollectionUtils.isNotEmpty(couponTemplateSkuList)){
            couponTemplateSkuList.forEach(elements -> {
                skuList.add(elements.getSkuId());
            });
            couponTemplateVO.setSkuList(skuList);
        }
        return couponTemplateVO;
    }

    @Override
    public List<CouponTemplate> findTemplateListBySkuId(Long skuId) {
        return this.couponTemplateMapper.findTemplateListBySkuId(skuId);
    }

    @Override
    public List<CouponTemplate> findTemplateListByCatId(Long catId) {

        return this.couponTemplateMapper.findTemplateListByCatId(catId);
    }

    @Override
    public List<CouponTemplate> findTemplateListByBrandId(Long brandId) {

        return this.couponTemplateMapper.findTemplateListByBrandId(brandId);
    }

    @Override
    public List<CouponTemplate> findAvailableTemplateListBySkuId(Long skuId) {

        return this.couponTemplateMapper.findAvailableTemplateListBySkuId(skuId);
    }

    @Override
    public List<CouponTemplate> findAvailableTemplateListByCatId(Long catId) {

        return this.couponTemplateMapper.findAvailableTemplateListByCatId(catId);
    }

    @Override
    public List<CouponTemplate> findAvailableTemplateListByBrandId(Long brandId) {

        return this.couponTemplateMapper.findAvailableTemplateListByBrandId(brandId);
    }

    @Override
    public List<CouponTemplate> findTemplateListCouponType(Long couponType) {

        return this.couponTemplateMapper.findTemplateListCouponType(couponType);
    }

    @Override
    public List<CouponTemplate> findTemplateListPromotionMethod(Long promotionMethod) {
        return this.couponTemplateMapper.findTemplateListPromotionMethod(promotionMethod);
    }

    @Override
    public List<CouponTemplate> findTemplateListAfterIssueTime(String issueTime) {
        return this.couponTemplateMapper.findTemplateListAfterIssueTime(issueTime);
    }

    @Override
    public List<CouponTemplate> findTemplateListBeforeIssueTime(String issueTime) {
        return this.couponTemplateMapper.findTemplateListBeforeIssueTime(issueTime);
    }

    @Override
    public List<CouponTemplate> findTemplateListBetweenIssueTime(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return this.couponTemplateMapper.findTemplateListBetweenIssueTime(map);
    }

    @Override
    public List<CouponTemplate> findExpiredTemplateList() {
        return this.couponTemplateMapper.findExpiredTemplateList();
    }

    @Override
    public List<CouponTemplate> findAll() {
        return this.couponTemplateMapper.findAll();
    }

    @Override
    public Long getReceivedCouponNum(Long templateId) {
        return this.couponTemplateMapper.getReceivedCouponNum(templateId);
    }

    @Override
    public Long getUsedCouponNum(Long templateId) {
        return this.couponTemplateMapper.getUsedCouponNum(templateId);
    }
}
