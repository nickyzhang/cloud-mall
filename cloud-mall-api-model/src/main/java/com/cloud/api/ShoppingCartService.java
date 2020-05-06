package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.po.cart.ShoppingCartParam;
import com.cloud.vo.cart.ShoppingCartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "/catalogcloud-mall-cart")
public interface ShoppingCartService {
    @RequestMapping(value = "/cart/addToCart",method = RequestMethod.POST)
    public ResponseResult addToCart(@RequestParam("skuId") Long skuId, @RequestParam Integer count);

    @RequestMapping(value = "/cart/add",method = RequestMethod.POST)
    public ResponseResult add(@RequestBody ShoppingCartParam cart);

    @RequestMapping(value = "/cart/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody ShoppingCartParam cart);

    @RequestMapping(value = "/cart/delete/{cartId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("cartId") Long cartId);

    @RequestMapping(value = "/cart/deleteBySkuId",method = RequestMethod.POST)
    public ResponseResult deleteBySkuId(@RequestParam Long skuId);

    @RequestMapping(value = "/cart/deleteByMemberId",method = RequestMethod.POST)
    public ResponseResult deleteByMemberId(@RequestParam Long memberId);

    @RequestMapping(value = "/cart/list/{cartId}",method = RequestMethod.GET)
    public ResponseResult<ShoppingCartInfo> findById(@PathVariable("cartId") Long cartId);

    @RequestMapping(value = "/cart/findByMemberId",method = RequestMethod.GET)
    public ResponseResult<List<ShoppingCartInfo>> findByMemberId(@RequestParam Long memberId);
}
