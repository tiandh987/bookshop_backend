package com.bookshop.service;

import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Appraise;
import com.bookshop.vo.AppraiseQueryModel;
import com.github.pagehelper.PageInfo;

public interface IAppraiseService {

    ServerResponse saveAppraise(Appraise appraise);

    ServerResponse<PageInfo> queryAppraiseByQueryModel(AppraiseQueryModel queryModel,int pageNum,int pageSize);

    ServerResponse havingAppraiseByOrderId (int orderId);
}
