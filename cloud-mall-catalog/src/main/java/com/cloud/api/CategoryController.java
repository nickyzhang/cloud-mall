package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.CategoryDto;
import com.cloud.model.catalog.Category;
import com.cloud.service.CategoryService;
import com.cloud.vo.catalog.CategoryVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/catalog/cat")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseResult save(@RequestBody CategoryVo categoryVo){
        ResponseResult result = new ResponseResult();
        if (categoryVo == null) {
            return result.failed("要添加的分类为空");
        }
        CategoryDto categoryDto = BeanUtils.copy(categoryVo, CategoryDto.class);
        int code = this.categoryService.save(categoryDto);
        return code == 0 ? result.failed("添加分类失败") : result.success("添加分类成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody CategoryVo categoryVo){
        ResponseResult result = new ResponseResult();
        if (categoryVo == null) {
            return result.failed("要更新的分类为空");
        }
        CategoryDto categoryDto = BeanUtils.copy(categoryVo, CategoryDto.class);
        int code = this.categoryService.update(categoryDto);
        return code == 0 ? result.failed("更新分类失败") : result.success("更新分类成功");
    }

    @PostMapping("/delete/{catId}")
    public ResponseResult delete(@PathVariable("catId") Long catId){
        ResponseResult result = new ResponseResult();
        int code = this.categoryService.delete(catId);
        return code == 0 ? result.failed("删除分类失败") : result.success("删除分类成功");
    }

    @GetMapping("/list/{catId}")
    public ResponseResult find(@PathVariable("catId") Long catId){
        ResponseResult result = new ResponseResult();
        CategoryDto categoryDto = this.categoryService.find(catId);
        return categoryDto == null ? result.failed("没有这个分类") : result.success(categoryDto);
    }

    @RequestMapping("/findAll")
    public ResponseResult findAll(){
        ResponseResult result = new ResponseResult();
        List<Category> categoryList = this.categoryService.findAll();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            result.success(categoryList);
        } else {
            result.failed("没有这个分类");
        }
        return result;
    }

    @GetMapping("/count")
    public ResponseResult count(){
        Long count = this.categoryService.count();
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg("操作成功");
        result.setData(count);
        return result;
    }

    @GetMapping(value = "/findCategoryByName")
    public ResponseResult findByName(@RequestParam("name")String name){
        ResponseResult result = new ResponseResult();
        CategoryDto categoryDto = this.categoryService.findByName(name);
        if (categoryDto != null) {
            result.success(categoryDto);
        } else {
            result.failed("没有这个分类");
        }
        return result;
    }

    @GetMapping(value = "/findCategoryListByBrandId")
    public ResponseResult findCategoriesByBrandId(@RequestParam("brandId") Long brandId){
        List<Category> categoryList = this.categoryService.findCategoriesByBrandId(brandId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            result.success(categoryList);
        } else {
            result.failed("没有这个分类");
        }
        return result;
    }

    @GetMapping(value = "/findCategoryListByAttributeNameId")
    public ResponseResult findCategoryListByAttributeNameId(@RequestParam("attributeNameId") Long attributeNameId){
        List<Category> categoryList = this.categoryService.findCategoriesByAttributeNameId(attributeNameId);
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            result.success(categoryList);
        } else {
            result.failed("没有这个分类");
        }
        return result;
    }
}
