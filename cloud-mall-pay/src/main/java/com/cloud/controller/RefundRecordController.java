package com.cloud.controller;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.RefundRecordDTO;
import com.cloud.model.pay.RefundRecord;
import com.cloud.service.RefundRecordService;
import com.cloud.vo.pay.RefundRecordVO;
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
public class RefundRecordController {

    @Autowired
    RefundRecordService refundRecordService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/trade/refund/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody RefundRecordVO refundRecordVO){
        ResponseResult result = new ResponseResult();
        RefundRecordDTO refundRecordDTO = BeanUtils.copy(refundRecordVO,RefundRecordDTO.class);
        refundRecordDTO.setId(this.genIdService.genId());
        refundRecordDTO.setDeleted(false);
        refundRecordDTO.setCreateTime(LocalDateTime.now());
        refundRecordDTO.setUpdateTime(LocalDateTime.now());
        int code = this.refundRecordService.save(refundRecordDTO);
        return code == 0 ? result.failed("添加退款记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody RefundRecordVO refundRecordVO){
        ResponseResult result = new ResponseResult();
        RefundRecordDTO refundRecordDTO = BeanUtils.copy(refundRecordVO,RefundRecordDTO.class);
        refundRecordDTO.setUpdateTime(LocalDateTime.now());
        this.refundRecordService.update(refundRecordDTO);
        return result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/deleteById",method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("id") Long id){
        ResponseResult result = new ResponseResult();
        int code = this.refundRecordService.deleteById(id);
        return code == 0 ? result.failed("删除退款记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/deleteByOrderId",method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        int code = this.refundRecordService.deleteByOrderId(orderId);
        return code == 0 ? result.failed("删除退款记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/deleteByOrderNo",method = RequestMethod.POST)
    public ResponseResult deleteByOrderNo(@RequestParam("orderNo") Long orderNo){
        ResponseResult result = new ResponseResult();
        int code = this.refundRecordService.deleteByOrderNo(orderNo);
        return code == 0 ? result.failed("删除退款记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/findByTradeNo",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByTradeNo(@RequestParam("tradeNo") String tradeNo){
        ResponseResult result = new ResponseResult();
        RefundRecordVO refundRecordVO = this.refundRecordService.findByTradeNo(tradeNo);
        return refundRecordVO == null ? result.failed("不存在此记录") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/findById",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findById(@RequestParam("id") Long id){
        ResponseResult result = new ResponseResult();
        RefundRecordVO refundRecordVO = this.refundRecordService.findById(id);
        return refundRecordVO == null ? result.failed("不存在此记录") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/findByOrderId",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        RefundRecordVO refundRecordVO = this.refundRecordService.findByOrderId(orderId);
        return refundRecordVO == null ? result.failed("不存在此记录") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/findByOrderNo",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByOrderNo(@RequestParam("orderNo") Long orderNo){
        ResponseResult result = new ResponseResult();
        RefundRecordVO refundRecordVO = this.refundRecordService.findByOrderNo(orderNo);
        return refundRecordVO == null ? result.failed("不存在此记录") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/refund/findByBuyerId",method = RequestMethod.GET)
    public ResponseResult<List<RefundRecord>> findByBuyerId(@RequestParam("buyerId") Long buyerId){
        ResponseResult<List<RefundRecord>> result = new ResponseResult<List<RefundRecord>>();
        List<RefundRecord> refundRecordList = this.refundRecordService.findByBuyerId(buyerId);
        return CollectionUtils.isEmpty(refundRecordList) ? result.success("不存在这个用户的退款记录",null) :
                result.success("成功",refundRecordList);
    }
}
