package com.bookshop.service;

import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Shipping;
import com.github.pagehelper.PageInfo;


public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> delete(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
