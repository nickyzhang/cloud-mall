package com.cloud.service;

import com.cloud.dto.cart.ShoppingCartDTO;
import com.cloud.model.cart.ShoppingCart;
import com.cloud.vo.cart.ShoppingCartInfo;
import java.util.List;

public interface ShoppingCartService {

    public int save(ShoppingCartDTO shoppingCartDTO);

    public int saveList(List<ShoppingCart> cartList);

    public int update(ShoppingCartDTO shoppingCartDTO);

    public int delete(Long cartId);

    public int deleteCartByMemberId(Long memberId);

    public int deleteCartBySkuId(Long skuId);

    public ShoppingCartInfo find(Long cartId);

    public List<ShoppingCartInfo> findCartByMemberId(Long memberId);

    public List<ShoppingCart> findAll();

    public void addCartToRedis(Long memberId, List<ShoppingCartInfo> cartList);

    public List<ShoppingCartInfo> getCartFromRedis(Long memberId);

    public int mergeShoppingCart(Long memberId, List<ShoppingCartDTO> redisCartList, List<ShoppingCartDTO> clientCartLists);
}
