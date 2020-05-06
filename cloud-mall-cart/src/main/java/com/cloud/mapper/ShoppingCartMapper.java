package com.cloud.mapper;

import com.cloud.model.cart.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    public int save(ShoppingCart shoppingCart);

    public int saveList(List<ShoppingCart> cartList);

    public int update(ShoppingCart shoppingCart);

    public int delete(Long cartId);

    public int deleteCartByMemberId(Long memberId);

    public int deleteCartBySkuId(Long skuId);

    public ShoppingCart find(Long cartId);

    public List<ShoppingCart> findCartByMemberId(Long memberId);

    public List<ShoppingCart> findAll();
}
