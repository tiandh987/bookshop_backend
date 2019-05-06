package com.bookshop.service;

import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Appraise;
import com.bookshop.vo.AppraiseQueryModel;
import com.bookshop.vo.AppraiseVo;

import java.util.List;

public interface IAppraiseService {

    ServerResponse saveAppraise(Appraise appraise);

    ServerResponse<List<AppraiseVo>> queryAppraiseByQueryModel(AppraiseQueryModel queryModel);

    ServerResponse havingAppraiseByOrderId (int orderId);
}
