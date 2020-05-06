package com.cloud.controller;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayNotifyDTO;
import com.cloud.dto.pay.PayRecordDTO;
import com.cloud.model.pay.PayNotify;
import com.cloud.service.PayNotifyLogService;
import com.cloud.service.PayNotifyService;
import com.cloud.vo.pay.PayNotifyVO;
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
public class PayNotifyController {

    @Autowired
    PayNotifyService payNotifyService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/trade/notify/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PayNotifyVO payNotifyVO){
        ResponseResult result = new ResponseResult();
        PayNotifyDTO payNotifyDTO = BeanUtils.copy(payNotifyVO,PayNotifyDTO.class);
        payNotifyDTO.setId(this.genIdService.genId());
        payNotifyDTO.setDeleted(false);
        payNotifyDTO.setCreateTime(LocalDateTime.now());
        payNotifyDTO.setUpdateTime(LocalDateTime.now());
        int code = this.payNotifyService.save(payNotifyDTO);
        return code == 0 ? result.failed("添加通知失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notify/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody  PayNotifyVO payNotifyVO){
        ResponseResult result = new ResponseResult();
        PayNotifyDTO payNotifyDTO = BeanUtils.copy(payNotifyVO,PayNotifyDTO.class);
        payNotifyDTO.setUpdateTime(LocalDateTime.now());
        int code = this.payNotifyService.update(payNotifyDTO);
        return code == 0 ? result.failed("更新通知失败") : result.success("成功",null);

    }

    @RequestMapping(value = "/trade/notify/deleteById", method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("id") Long id){
        ResponseResult result = new ResponseResult();
        int code = this.payNotifyService.deleteById(id);
        return code == 0 ? result.failed("删除通知失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notify/deleteByOrderId", method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        int code = this.payNotifyService.deleteByOrderId(orderId);
        return code == 0 ? result.failed("删除通知失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notify/findById", method = RequestMethod.GET)
    public ResponseResult<PayNotifyVO> findById(@RequestParam("id") Long id){
        ResponseResult<PayNotifyVO> result = new ResponseResult<PayNotifyVO>();
        PayNotifyVO payRecordVO = this.payNotifyService.findById(id);
        return payRecordVO == null ? result.success("不存在这个记录",null) :
                result.success("成功",payRecordVO);
    }

    @RequestMapping(value = "/trade/notify/findByOrderId", method = RequestMethod.GET)
    public ResponseResult<List<PayNotify>> findByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult<List<PayNotify>> result = new ResponseResult<List<PayNotify>>();
        List<PayNotify> payNotifyList = this.payNotifyService.findByOrderId(orderId);
        return CollectionUtils.isEmpty(payNotifyList) ? result.success("不存在这个记录",null) :
                result.success("成功",payNotifyList);
    }
}
