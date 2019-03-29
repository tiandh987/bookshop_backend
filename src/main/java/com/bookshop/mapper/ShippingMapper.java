package com.bookshop.mapper;

import com.bookshop.pojo.Shipping;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    //-------------------
    int deleteByShippingUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    int updateByShipping(Shipping shipping);

    Shipping selectByShippingIdUserId(@Param("userId")Integer userId,@Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}