package com.bookshop.mapper;

import com.bookshop.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //-----------------------------------
    Cart selectByUserIdProductId(@Param(value = "userId")Integer userId, @Param(value = "productId") Integer productId);

    List<Cart> selectByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIdList") List<String> productIdList);

    int checkedOrUnchecked(@Param("userId") Integer userId,@Param(value = "productId") Integer productId,@Param("checked") Integer checked);

    int selectCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}