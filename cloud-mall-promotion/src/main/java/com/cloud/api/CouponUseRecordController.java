package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.CouponUseRecordDTO;
import com.cloud.model.promotion.CouponUseRecord;
import com.cloud.service.CouponUseRecordService;
import com.cloud.vo.promotion.CouponUseRecordVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CouponUseRecordController {

    @Autowired
    CouponUseRecordService couponUseRecordService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/couponUseRecord/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加优惠券使用记录优惠券使用记录")
    public ResponseResult batchSave(@RequestBody CouponUseRecordVO couponUseRecordVO){
        ResponseResult result = new ResponseResult();
        if (couponUseRecordVO == null) {
            return result.failed("要保存的优惠券使用记录为空");
        }
        CouponUseRecordDTO couponUseRecordDTO = BeanUtils.copy(couponUseRecordVO, CouponUseRecordDTO.class);
        couponUseRecordDTO.setId(this.genIdService.genId());
        couponUseRecordDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        couponUseRecordDTO.setCreateTime(now);
        couponUseRecordDTO.setUpdateTime(now);
        int code = this.couponUseRecordService.save(couponUseRecordDTO);
        return code == 0 ? result.failed("保存失败") : result.success("保存成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/batchAdd",method = RequestMethod.POST)
    @ApiOperation(value = "添加优惠券使用记录优惠券使用记录")
    public ResponseResult save(@RequestBody List<CouponUseRecordVO> couponUseRecordList){
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(couponUseRecordList)) {
            return result.failed("要保存的优惠券使用记录为空");
        }
        List<CouponUseRecordDTO> couponUseRecordDTOList = BeanUtils.copy(couponUseRecordList, CouponUseRecordDTO.class);
        for (CouponUseRecordDTO couponUseRecordDTO : couponUseRecordDTOList) {
            couponUseRecordDTO.setId(this.genIdService.genId());
            couponUseRecordDTO.setDeleted(Boolean.FALSE);
            LocalDateTime now = LocalDateTime.now();
            couponUseRecordDTO.setCreateTime(now);
            couponUseRecordDTO.setUpdateTime(now);
        }
        int code = this.couponUseRecordService.saveList(couponUseRecordDTOList);
        return code == 0 ? result.failed("保存失败") : result.success("保存成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新优惠券使用记录")
    public ResponseResult update(@RequestBody CouponUseRecordVO couponUseRecordVO){
        ResponseResult result = new ResponseResult();
        if (couponUseRecordVO == null) {
            return result.failed("要更新的优惠券使用记录为空");
        }
        CouponUseRecordDTO couponUseRecordDTO = BeanUtils.copy(couponUseRecordVO, CouponUseRecordDTO.class);
        int code = this.couponUseRecordService.update(couponUseRecordDTO);
        return code == 0 ? result.failed("更新失败") : result.success("更新成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/delete/{id}",method = RequestMethod.POST)
    @ApiOperation(value = "删除优惠券使用记录")
    public ResponseResult delete(@PathVariable("id") Long id){
        ResponseResult result = new ResponseResult();
        if (id == null) {
            return result.failed("要删除优惠券使用记录ID为空");
        }
        int code = this.couponUseRecordService.delete(id);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/deleteUseRecordByUserId",method = RequestMethod.POST)
    @ApiOperation(value = "根据用户ID删除优惠券使用记录")
    public ResponseResult deleteUseRecordByUserId(@RequestParam("userId") Long userId){
        ResponseResult result = new ResponseResult();
        if (userId == null) {
            return result.failed("用户ID参数为空");
        }
        int code = this.couponUseRecordService.deleteUseRecordByUserId(userId);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/deleteUseRecordByCouponId",method = RequestMethod.POST)
    @ApiOperation(value = "根据优惠券ID删除优惠券使用记录")
    public ResponseResult deleteUseRecordByCouponId(@RequestParam("couponId") Long couponId){
        ResponseResult result = new ResponseResult();
        if (couponId == null) {
            return result.failed("优惠券ID参数为空");
        }
        int code = this.couponUseRecordService.deleteUseRecordByCouponId(couponId);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/couponUseRecord/list/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找优惠券使用记录")
    public ResponseResult<CouponUseRecordVO> find(@PathVariable("id") Long id){
        ResponseResult result = new ResponseResult();
        if (id == null) {
            return result.failed("要查询优惠券使用记录ID参数为空");
        }
        CouponUseRecordVO couponUseRecordVO = this.couponUseRecordService.find(id);
        return couponUseRecordVO == null ? result.success("该优惠券使用记录不存在",null) : result.success("查询成功",couponUseRecordVO);
    }

    @RequestMapping(value = "/couponUseRecord/findUseRecordByCouponNo",method = RequestMethod.GET)
    @ApiOperation(value = "根据优惠券码查找优惠券使用记录")
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponNo(@RequestParam("couponNo") String couponNo){
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(couponNo)) {
            return result.failed("优惠券码参数为空");
        }
        CouponUseRecordVO couponUseRecordVO = this.couponUseRecordService.findUseRecordByCouponNo(couponNo);
        return couponUseRecordVO == null ? result.success("没有优惠券码的使用记录",null) :
                result.success("查询成功",couponUseRecordVO);
    }

    @RequestMapping(value = "/couponUseRecord/findUseRecordByCouponId",method = RequestMethod.GET)
    @ApiOperation(value = "根据优惠券id查找优惠券使用记录")
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponId(@RequestParam("couponId") Long couponId){
        ResponseResult result = new ResponseResult();
        if (couponId == null) {
            return result.failed("优惠券id参数为空");
        }
        CouponUseRecordVO couponUseRecordVO = this.couponUseRecordService.findUseRecordByCouponId(couponId);
        return couponUseRecordVO == null ? result.success("没有优惠券的优惠券使用记录",null) :
                result.success("查询成功",couponUseRecordVO);
    }

    @RequestMapping(value = "/couponUseRecord/findUseRecordListByUserId",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查找优惠券使用记录")
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByUserId(@RequestParam("userId") Long userId){
        ResponseResult result = new ResponseResult();
        if (userId == null) {
            return result.failed("用户id参数为空");
        }
        List<CouponUseRecord> couponUseRecordList= this.couponUseRecordService.findUseRecordListByUserId(userId);
        return CollectionUtils.isEmpty(couponUseRecordList) ? result.success("没有该用户的优惠券使用记录",null) :
                result.success("查询成功",couponUseRecordList);
    }

    @RequestMapping(value = "/couponUseRecord/findUseRecordListByOrderId",method = RequestMethod.GET)
    @ApiOperation(value = "根据订单id查找优惠券使用记录")
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByOrderId(@RequestParam("orderId") Long orderId){
        ResponseResult result = new ResponseResult();
        if (orderId == null) {
            return result.failed("订单id参数为空");
        }
        List<CouponUseRecord> couponUseRecordList= this.couponUseRecordService.findUseRecordListByOrderId(orderId);
        return CollectionUtils.isEmpty(couponUseRecordList) ? result.success("没有该订单的优惠券使用记录",null) :
                result.success("查询成功",couponUseRecordList);
    }

    @RequestMapping(value = "/couponUseRecord/findUseRecordByOrderNo",method = RequestMethod.GET)
    @ApiOperation(value = "根据订单业务号查找优惠券使用记录")
    public ResponseResult<List<CouponUseRecord>> findUseRecordByOrderNo(@RequestParam("orderNo") String orderNo){
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(orderNo)) {
            return result.failed("订单业务号参数为空");
        }
        List<CouponUseRecord> couponUseRecordList= this.couponUseRecordService.findUseRecordByOrderNo(orderNo);
        return CollectionUtils.isEmpty(couponUseRecordList) ? result.success("没有该订单号的优惠券使用记录",null) :
                result.success("查询成功",couponUseRecordList);
    }

    @RequestMapping(value = "/couponUseRecord/findAll",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找优惠券使用记录")
    public ResponseResult<List<CouponUseRecord>> findAll(){
        ResponseResult result = new ResponseResult();
        List<CouponUseRecord> couponUseRecordList= this.couponUseRecordService.findAll();
        return CollectionUtils.isEmpty(couponUseRecordList) ? result.success("没有优惠券使用记录",null) :
                result.success("查询成功",couponUseRecordList);
    }

    @RequestMapping(value = "/couponUseRecord/getUseNumByTemplateId",method = RequestMethod.GET)
    @ApiOperation(value = "根据优惠券模板ID查找优惠券使用记录数")
    public ResponseResult getUseNumByTemplateId(@RequestParam("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        if (templateId == null) {
            return result.failed("优惠券模板id参数为空");
        }
        Long usedNum = this.couponUseRecordService.getUseNumByTemplateId(templateId);
        return usedNum != null ? result.success("没有该优惠券模板使用记录",null) : result.success("查询成功",usedNum);
    }
}
