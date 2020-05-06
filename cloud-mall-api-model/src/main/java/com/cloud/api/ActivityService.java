package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.Activity;
import com.cloud.vo.promotion.ActivityVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface ActivityService {

    @RequestMapping(value = "/act/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody ActivityVO activityVO);

    @RequestMapping(value = "/act/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody ActivityVO activityVO);

    @RequestMapping(value = "/act/delete/{activityId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("activityId") Long activityId);

    @RequestMapping(value = "/act/list/{activityId}",method = RequestMethod.GET)
    public ResponseResult<ActivityVO> find(@PathVariable("activityId") Long activityId);

    @RequestMapping(value = "/act/findBySkuId",method = RequestMethod.GET)
    public ResponseResult<List<Activity>> findBySkuId(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/act/findByActivityType",method = RequestMethod.GET)
    public ResponseResult<List<Activity>> findByActivityType(@RequestParam("actType") Integer actType);

    @RequestMapping(value = "/act/findActivityListByTimeRange",method = RequestMethod.GET)
    public ResponseResult<List<Activity>> findActivityListByTimeRange(
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

    @RequestMapping(value = "/act/findAll",method = RequestMethod.GET)
    public ResponseResult<List<ActivityVO>> findAll();

    @RequestMapping(value = "/act/count",method = RequestMethod.GET)
    public ResponseResult count();

    @RequestMapping(value = "/act/findActsBySkuIds",method = RequestMethod.GET)
    public ResponseResult<List<Activity>> findActsBySkuIds(@RequestBody Long[] skuIds);
}
