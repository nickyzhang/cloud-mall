package com.cloud.controller;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.PayNotifyLogDTO;
import com.cloud.model.pay.PayNotifyLog;
import com.cloud.service.PayNotifyLogService;
import com.cloud.vo.pay.PayNotifyLogVO;
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
public class PayNotifyLogController {

    @Autowired
    PayNotifyLogService payNotifyLogService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/trade/notifyLog/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PayNotifyLogVO payNotifyLogVO){
        ResponseResult result = new ResponseResult();
        PayNotifyLogDTO payNotifyLogDTO = BeanUtils.copy(payNotifyLogVO,PayNotifyLogDTO.class);
        payNotifyLogDTO.setId(this.genIdService.genId());
        payNotifyLogDTO.setDeleted(false);
        payNotifyLogDTO.setCreateTime(LocalDateTime.now());
        payNotifyLogDTO.setUpdateTime(LocalDateTime.now());
        int code = this.payNotifyLogService.save(payNotifyLogDTO);
        return code == 0 ? result.failed("添加通知日志失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notifyLog/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody PayNotifyLogVO payNotifyLogVO){
        ResponseResult result = new ResponseResult();
        PayNotifyLogDTO payNotifyLogDTO = BeanUtils.copy(payNotifyLogVO,PayNotifyLogDTO.class);
        payNotifyLogDTO.setUpdateTime(LocalDateTime.now());
        int code = this.payNotifyLogService.update(payNotifyLogDTO);
        return code == 0 ? result.failed("更新通知记录失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notifyLog/deleteById",method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("id") Long id){
        ResponseResult result = new ResponseResult();
        int code = this.payNotifyLogService.deleteById(id);
        return code == 0 ? result.failed("删除通知日志失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notifyLog/deleteByOrderId",method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        int code = this.payNotifyLogService.deleteByOrderId(orderId);
        return code == 0 ? result.failed("删除通知日志失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notifyLog/deleteByNotifyId",method = RequestMethod.POST)
    public ResponseResult deleteByNotifyId(@RequestParam("notifyId") Long notifyId){
        ResponseResult result = new ResponseResult();
        int code = this.payNotifyLogService.deleteByNotifyId(notifyId);
        return code == 0 ? result.failed("删除通知日志失败") : result.success("成功",null);
    }

    @RequestMapping(value = "/trade/notifyLog/findById",method = RequestMethod.GET)
    public ResponseResult<PayNotifyLogVO> findById(@RequestParam("id")  Long id){
        ResponseResult<PayNotifyLogVO> result = new ResponseResult<PayNotifyLogVO>();
        PayNotifyLogVO payNotifyLogVO = this.payNotifyLogService.findById(id);
        return payNotifyLogVO == null ? result.success("不存在这个记录",null) :
                result.success("成功",payNotifyLogVO);
    }

    @RequestMapping(value = "/trade/notifyLog/findByOrderId",method = RequestMethod.GET)
    public ResponseResult<List<PayNotifyLog>> findByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult<List<PayNotifyLog>> result = new ResponseResult<List<PayNotifyLog>>();
        List<PayNotifyLog> payNotifyLogList = this.payNotifyLogService.findByOrderId(orderId);
        return CollectionUtils.isEmpty(payNotifyLogList) ? result.success("不存在记录",null) :
                result.success("成功",payNotifyLogList);
    }

    @RequestMapping(value = "/trade/notifyLog/findByNotifyId",method = RequestMethod.GET)
    public ResponseResult<List<PayNotifyLog>> findByNotifyId(@RequestParam("notifyId") Long notifyId){
        ResponseResult<List<PayNotifyLog>> result = new ResponseResult<List<PayNotifyLog>>();
        List<PayNotifyLog> payNotifyLogList = this.payNotifyLogService.findByNotifyId(notifyId);
        return CollectionUtils.isEmpty(payNotifyLogList) ? result.success("不存在记录",null) :
                result.success("成功",payNotifyLogList);
    }
}
