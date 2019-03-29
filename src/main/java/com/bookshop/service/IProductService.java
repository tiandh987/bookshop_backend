package com.bookshop.service;

import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Product;
import com.bookshop.pojo.ProductWithBLOBs;
import com.bookshop.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

public interface IProductService {

    ServerResponse saveOrUpdateProduct(ProductWithBLOBs product);

    ServerResponse setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> productSearch(Integer productId,String productName,Integer pageNum,Integer pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,
                                                         Integer pageNum,Integer pageSize,String orderBy);
}
