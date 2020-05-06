package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.service.RestService;
import com.cloud.vo.aggr.SkuInfo;
import com.cloud.vo.catalog.SkuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CatalogController {

    @Autowired
    RestService restService;

    /**
     * #1 页面管理系统(PMS)：可以创建page name,并且可以选择过滤条件或者搜索条件去ElasticSearch查询数据作为当前页面的初始数据
     * #2 当客户端请求到来的时候，先从PMS根据解析的Page Name获取页面以及对应的初始数据
     * #3 计算refinement和desc
     */
    @RequestMapping(value = "/list/{skuId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<SkuVo> getSkuInfo(@PathVariable(value = "skuId") Long skuId) {
        log.info("[EMall][CatalogController]skuId = {}",skuId);
        JSONObject skuJSON = this.restService.getForObject("http://api.cloud.com/catalog/sku/list/"+skuId, JSONObject.class);
        TypeReference<ResponseResult<SkuVo>> skuType= new TypeReference<ResponseResult<SkuVo>>(){};
        ResponseResult<SkuVo> skuResult = skuJSON.toJavaObject(skuType);
        if (skuResult.getCode() != ResultCodeEnum.OK.getCode()) {
            log.info("[EMall][CatalogController][查询sku失败]");
            return null;
        }
        return skuResult;
    }
}
