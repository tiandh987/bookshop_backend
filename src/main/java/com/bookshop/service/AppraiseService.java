package com.bookshop.service;

import com.bookshop.common.ServerResponse;
import com.bookshop.mapper.AppraiseMapper;
import com.bookshop.pojo.Appraise;
import com.bookshop.vo.AppraiseQueryModel;
import com.bookshop.vo.AppraiseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppraiseService implements IAppraiseService {
    @Autowired
    private AppraiseMapper appraiseMapper;

    //保存评论的方法
    @Override
    public ServerResponse saveAppraise(Appraise appraise) {
        try {
            //校验数据
            String msg = "";
            if (appraise.getGrade() == null || appraise.getGrade() < 0) {
                msg = "保存失败，评分不能为空";
            } else if (appraise.getOrderId() == null || appraise.getOrderId() < 0) {
                msg = "保存失败，订单编号不能为空";
            } else if (appraise.getProductId() == null || appraise.getProductId() < 0) {
                msg = "保存失败，商品编号不能为空";
            } else if (appraise.getUserId() == null || appraise.getUserId() < 0) {
                msg = "保存失败，用户编号不能为空";
            } else if (appraise.getText() == null || "".equals(appraise.getText())) {
                msg = "保存失败，评价内容不能为空";
            }
            if (!"".equals(msg)) {
                return ServerResponse.createByErrorMessage(msg);
            }
            //插入数据
            int row = appraiseMapper.insert(appraise);
            if (row > 0) {
                return ServerResponse.createBySuccess();
            }
        } catch (Exception ex) {
            return ServerResponse.createByErrorMessage("插入出现异常，请联系管理员。");
        }
        return ServerResponse.createByErrorMessage("插入失败，请稍后重试，或联系管理员。");
    }

    //查询商品的评论信息 带过滤
    @Override
    public ServerResponse<List<AppraiseVo>> queryAppraiseByQueryModel(AppraiseQueryModel queryModel) {
        try {
            List<AppraiseVo> appraiseVos = appraiseMapper.selectByQueryModel(queryModel.getProductId(), queryModel.getOrderId(), queryModel.getGrade());
            return ServerResponse.createBySuccess(appraiseVos);
        } catch (Exception ex) {
            String msg = "查询评价出现异常，请联系管理员。";
            return ServerResponse.createByErrorMessage(msg);
        }
    }

    //查询是否已评论 根据订单id 查询
    @Override
    public ServerResponse havingAppraiseByOrderId(int orderId) {
        try {
            int count = appraiseMapper.selectCountByOrderId(orderId);
            if (count > 0) {
                return ServerResponse.createBySuccess();
            }
        } catch (Exception ex) {
            return ServerResponse.createByErrorMessage("查询是否评价出现异常，请联系管理员。");
        }
        return ServerResponse.createByError();
    }
}
