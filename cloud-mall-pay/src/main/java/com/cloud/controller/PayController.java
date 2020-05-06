package com.cloud.controller;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayRecordDTO;
import com.cloud.model.pay.PayRecord;
import com.cloud.po.pay.PayRecordParam;
import com.cloud.service.PayRecordService;
import com.cloud.vo.pay.PayRecordVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PayController {

    @Autowired
    PayRecordService payRecordService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/trade/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PayRecordParam payRecordParam){
        ResponseResult result = new ResponseResult();
        PayRecordDTO payRecordDTO = BeanUtils.copy(payRecordParam,PayRecordDTO.class);
        payRecordDTO.setTradeId(this.genIdService.genId());
        payRecordDTO.setDeleted(false);
        payRecordDTO.setCreateTime(LocalDateTime.now());
        payRecordDTO.setUpdateTime(LocalDateTime.now());
        this.payRecordService.save(payRecordDTO);
        // 扣减库存
        // 扣减优惠券
        // 增加会员积分
        return result.success("成功",null);
    }

    @RequestMapping(value = "/trade/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody PayRecordVO payRecordVO) {
        ResponseResult result = new ResponseResult();
        PayRecordDTO payRecordDTO = BeanUtils.copy(payRecordVO,PayRecordDTO.class);
        payRecordDTO.setUpdateTime(LocalDateTime.now());
        this.payRecordService.update(payRecordDTO);
        return result.success("成功",null);
    }

    @RequestMapping(value = "/trade/deleteById",method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("tradeId") Long tradeId){
        ResponseResult result = new ResponseResult();
        int code = this.payRecordService.deleteById(tradeId);
        return code == 0 ? result.failed("删除交易记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/deleteByOrderId",method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        int code = this.payRecordService.deleteByOrderId(orderId);
        return code == 0 ? result.failed("删除交易记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/deleteByOrderNo",method = RequestMethod.POST)
    public ResponseResult deleteByOrderNo(@RequestParam("orderNo") Long orderNo) {
        ResponseResult result = new ResponseResult();
        int code = this.payRecordService.deleteByOrderNo(orderNo);
        return code == 0 ? result.failed("删除交易记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/findByTradeNo",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByTradeNo(@RequestParam("tradeNo") String tradeNo){
        ResponseResult<PayRecordVO> result = new ResponseResult<PayRecordVO>();
        PayRecordVO payRecordVO = this.payRecordService.findByTradeNo(tradeNo);
        return payRecordVO == null ? result.failed("删除交易记录失败") : result.success("成功",payRecordVO);
    }

    @RequestMapping(value = "/trade/findById",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findById(@RequestParam("tradeId") Long tradeId){
        ResponseResult<PayRecordVO> result = new ResponseResult<PayRecordVO>();
        PayRecordVO payRecordVO = this.payRecordService.findById(tradeId);
        return payRecordVO == null ? result.failed("删除交易记录失败") : result.success("成功",payRecordVO);
    }

    @RequestMapping(value = "/trade/findByOrderId",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult<PayRecordVO> result = new ResponseResult<PayRecordVO>();
        PayRecordVO payRecordVO = this.payRecordService.findByOrderId(orderId);
        return payRecordVO == null ? result.failed("删除交易记录失败") : result.success("成功",payRecordVO);
    }

    @RequestMapping(value = "/trade/findByOrderNo",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByOrderNo(@RequestParam("orderNo") Long orderNo){
        ResponseResult<PayRecordVO> result = new ResponseResult<PayRecordVO>();
        PayRecordVO payRecordVO = this.payRecordService.findByOrderNo(orderNo);
        return payRecordVO == null ? result.failed("删除交易记录失败") : result.success("成功",payRecordVO);
    }

    @RequestMapping(value = "/trade/findByBuyerId",method = RequestMethod.GET)
    public ResponseResult<List<PayRecord>> findByBuyerId(@RequestParam("buyerId") Long buyerId){
        ResponseResult<List<PayRecord>> result = new ResponseResult<List<PayRecord>>();
        List<PayRecord> payRecordList= this.payRecordService.findByBuyerId(buyerId);
        return CollectionUtils.isEmpty(payRecordList) ? result.failed("删除交易记录失败") :
                result.success("成功",payRecordList);
    }

}
