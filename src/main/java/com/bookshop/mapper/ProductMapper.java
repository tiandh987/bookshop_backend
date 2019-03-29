package com.bookshop.mapper;

import com.bookshop.pojo.Product;
import com.bookshop.pojo.ProductWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    ProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    int updateByPrimaryKey(Product record);

    //--------------------
    List<Product> selectList();

    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId")Integer productId);

    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName,@Param("categoryIdList") List<Integer> categoryIdList);

    //一定要用Integer接收返回值，因为int无法为NULL，考虑到很多商品已经被删除
    Integer selectStockByProductId(Integer productId);
}