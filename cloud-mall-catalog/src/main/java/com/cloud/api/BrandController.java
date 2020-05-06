package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.BrandDto;
import com.cloud.model.catalog.Brand;
import com.cloud.service.BrandService;
import com.cloud.vo.catalog.BrandVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/catalog/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping("/add")
    public ResponseResult save(@RequestBody BrandVo brandVo){
        ResponseResult result = new ResponseResult();
        if (brandVo == null) {
            return result.failed("要添加的品牌为空");
        }
        BrandDto brandDto = BeanUtils.copy(brandVo, BrandDto.class);
        int code = this.brandService.save(brandDto);
        return code == 0 ? result.failed("添加属性值失败") : result.success("添加属性值成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody BrandVo brandVo){
        ResponseResult result = new ResponseResult();
        if (brandVo == null) {
            return result.failed("要更新的品牌为空");
        }
        BrandDto brandDto = BeanUtils.copy(brandVo, BrandDto.class);
        int code = this.brandService.save(brandDto);
        return code == 0 ? result.failed("更新品牌失败") : result.success("更新品牌成功");
    }

    @PostMapping("/delete/{brandId}")
    public ResponseResult delete(@PathVariable("brandId") Long brandId){
        ResponseResult result = new ResponseResult();
        int code = this.brandService.delete(brandId);
        return code == 0 ? result.failed("删除品牌失败") : result.success("删除品牌成功");
    }

    @GetMapping("/list/{brandId}")
    public ResponseResult find(@PathVariable("brandId") Long brandId){
        ResponseResult result = new ResponseResult();
        BrandDto brandDto = this.brandService.find(brandId);
        return brandDto == null ? result.failed("没有这个品牌") : result.success(brandDto);
    }

    @GetMapping("/findAll")
    public ResponseResult findAll(){
        List<Brand> brandList = this.brandService.findAll();
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(brandList)) {
            result.success(brandList);
        } else {
            result.failed("没有这个品牌");
        }

        return result;
    }

    @GetMapping("/count")
    public ResponseResult count(){
        ResponseResult result = new ResponseResult();
        Long count = this.brandService.count();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg("操作成功");
        result.setData(count);
        return result;
    }

    @GetMapping(value = "/findBrandByName")
    public ResponseResult findByName(@RequestParam("name") String name){
        ResponseResult result = new ResponseResult();
        BrandDto brandDto = this.brandService.findByName(name);
        return brandDto == null ? result.failed("没有这个品牌") : result.success(brandDto);
    }

    @GetMapping(value = "/findBrandListByCategoryId")
    public ResponseResult findBrandsByCategoryId(@RequestParam("catId") Long categoryId){
        List<Brand> brandList = this.brandService.findBrandsByCategoryId(categoryId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(brandList)) {
            result.success(brandList);
        } else {
            result.failed("该分类下没有任何品牌");
        }
        return result;
    }
}
