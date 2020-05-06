package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.AttributeValueDto;
import com.cloud.model.catalog.AttributeValue;
import com.cloud.service.AttributeValueService;
import com.cloud.vo.catalog.AttributeValueVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/catalog/attrValue")
public class AttributeValueController {

    @Autowired
    AttributeValueService attributeValueService;

    @PostMapping("/add")
    public ResponseResult save(@RequestBody AttributeValueVo attributeValueVo) {
        ResponseResult result = new ResponseResult();
        if (attributeValueVo == null) {
            return result.failed("要添加的属性值为空");
        }
        AttributeValueDto attributeValueDto = BeanUtils.copy(attributeValueVo, AttributeValueDto.class);
        int code = this.attributeValueService.save(attributeValueDto);
        return code == 0 ? result.failed("添加属性值失败") : result.success("添加属性值成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody AttributeValueVo attributeValueVo) {
        ResponseResult result = new ResponseResult();
        if (attributeValueVo == null) {
            return result.failed("要更新的属性值为空");
        }
        AttributeValueDto attributeValueDto = BeanUtils.copy(attributeValueVo, AttributeValueDto.class);
        int code = this.attributeValueService.update(attributeValueDto);
        return code == 0 ? result.failed("更新属性值失败") : result.success("更新属性值成功");
    }

    @PostMapping("/delete/{attributeValueId}")
    public ResponseResult delete(@PathVariable("attributeValueId") Long attributeValueId) {
        ResponseResult result = new ResponseResult();
        int code = this.attributeValueService.delete(attributeValueId);
        return code == 0 ? result.failed("更新属性值失败") : result.success("更新属性值成功");
    }

    @GetMapping("/list/{attributeValueId}")
    public ResponseResult find(@PathVariable("attributeValueId") Long attributeValueId) {
        AttributeValueDto attributeValueDto = this.attributeValueService.find(attributeValueId);
        ResponseResult result = new ResponseResult();
        if (attributeValueDto != null) {
            result.success(attributeValueDto);
        } else {
            result.failed("无法获取属性值");
        }
        return result;
    }

    @GetMapping("/findAll")
    public ResponseResult findAll() {
        ResponseResult result = new ResponseResult();
        List<AttributeValue> attributeValueList = this.attributeValueService.findAll();
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            result.success(attributeValueList);
        } else {
            result.failed("无法获取属性值");
        }
        return result;
    }

    @GetMapping("/count")
    public ResponseResult count() {
        Long count = this.attributeValueService.count();
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg("操作成功");
        result.setData(count);
        return result;
    }

    @GetMapping("/findAttributeValueListByAttributeNameId")
    public ResponseResult findAttributeValueListByAttributeNameId(@RequestParam("attrNameId") Long attrNameId ) {
        ResponseResult result = new ResponseResult();
        List<AttributeValue> attributeValueList = this.attributeValueService.findAttributeValueListByAttributeNameId(attrNameId);
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            result.success(attributeValueList);
        } else {
            result.failed("无法获取属性值");
        }
        return result;
    }

    @GetMapping("/findAttributeValueListByProductId")
    public ResponseResult findAttributeValueListByProductId(@RequestParam("productId") Long productId ) {
        ResponseResult result = new ResponseResult();
        List<AttributeValue> attributeValueList = this.attributeValueService.findAttributeValueListByProductId(productId);
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            result.success(attributeValueList);
        } else {
            result.failed("无法获取属性值");
        }
        return result;
    }

    @GetMapping("/findAttributeValueListBySkuId")
    public ResponseResult findAttributeValueListBySkuId(@RequestParam("skuId") Long skuId ) {
        ResponseResult result = new ResponseResult();
        List<AttributeValue> attributeValueList = this.attributeValueService.findAttributeValueListBySkuId(skuId);
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            result.success(attributeValueList);
        } else {
            result.failed("无法获取属性值");
        }
        return result;
    }
}
