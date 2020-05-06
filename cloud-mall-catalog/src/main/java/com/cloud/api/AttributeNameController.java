package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.AttributeNameDto;
import com.cloud.model.catalog.AttributeName;
import com.cloud.service.AttributeNameService;
import com.cloud.vo.catalog.AttributeNameVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/attrName")
public class AttributeNameController {

    @Autowired
    AttributeNameService attributeNameService;

    @PostMapping("/add")
    public ResponseResult save(@RequestBody AttributeNameVo attributeNameVo){
        ResponseResult result = new ResponseResult();
        if (attributeNameVo == null) {
            return result.failed("要添加的属性名为空");
        }
        AttributeNameDto attributeNameDto = BeanUtils.copy(attributeNameVo,AttributeNameDto.class);
        int code = this.attributeNameService.save(attributeNameDto);
        return code == 0 ? result.failed("添加属性名失败") : result.success("添加属性名成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody AttributeNameVo attributeNameVo){
        ResponseResult result = new ResponseResult();
        if (attributeNameVo == null) {
            return result.failed("要更新的属性名为空");
        }

        AttributeNameDto attributeNameDto = BeanUtils.copy(attributeNameVo,AttributeNameDto.class);
        int code = this.attributeNameService.update(attributeNameDto);
        return code == 0 ? result.failed("更新属性名失败") : result.success("更新属性名成功");
    }

    @PostMapping("/delete/{attributeNameId}")
    public ResponseResult delete(@PathVariable("attributeNameId") Long attributeNameId){
        ResponseResult result = new ResponseResult();
        int code = this.attributeNameService.delete(attributeNameId);
        return code == 0 ? result.failed("删除属性名失败") : result.success("删除属性名成功");
    }

    @GetMapping("/list/{attributeNameId}")
    public ResponseResult find(@PathVariable("attributeNameId") Long attributeNameId){
        AttributeNameDto attributeNameDto = this.attributeNameService.find(attributeNameId);
        ResponseResult result = new ResponseResult();
        if (attributeNameDto != null) {
            result.success(attributeNameDto);
        } else {
            result.failed("无法获取属性名");
        }
        return result;
    }

    @GetMapping("/findAll")
    public ResponseResult findAll(){
        List<AttributeName> attributeNameList = this.attributeNameService.findAll();
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(attributeNameList)) {
            result.success(attributeNameList);
        } else {
            result.failed("没有任何属性名");
        }

        return result;
    }

    @GetMapping("/count")
    public ResponseResult count() {
        Long count = this.attributeNameService.count();
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg("操作成功");
        result.setData(count);
        return result;
    }

    @GetMapping(value="/findAttributeNamesByCategoryId",params = {"categoryId"})
    public ResponseResult findAttributeNamesByCategoryId(Long categoryId){
        List<AttributeName> attributeNameList = this.attributeNameService.findAttributeNamesByCategoryId(categoryId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(attributeNameList)) {
            result.success(attributeNameList);
        } else {
            result.failed("没有属性名");
        }
        return result;
    }
}
