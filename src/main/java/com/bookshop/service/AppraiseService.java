package com.bookshop.service;

import com.bookshop.common.Const;
import com.bookshop.common.ServerResponse;
import com.bookshop.mapper.AppraiseMapper;
import com.bookshop.mapper.OrderItemMapper;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.pojo.Appraise;
import com.bookshop.pojo.Order;
import com.bookshop.pojo.OrderItem;
import com.bookshop.util.DateTimeUtil;
import com.bookshop.vo.AppraiseQueryModel;
import com.bookshop.vo.AppraiseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppraiseService implements IAppraiseService {
    @Autowired
    private AppraiseMapper appraiseMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;

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
                //修改此订单下该商品是否已评价状态 0:未评价 1：已评价
                orderItemMapper.updateIsAppraise(appraise.getUserId(), appraise.getOrderId(),
                        appraise.getProductId(), 1);
                //判断订单中的每个商品是否都已经被评价
                List<OrderItem> orderItemList =
                        orderItemMapper.getByOrderNoUserId(appraise.getOrderId(), appraise.getUserId());
                boolean flag = true;//假设每个商品都被评价
                for (OrderItem orderItem : orderItemList) {
                    if (0 == orderItem.getIsAppraise()){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    Order order = orderMapper.selectByOrderNo(appraise.getOrderId());
                    if (order != null){
                        if(order.getStatus() == Const.OrderStatus.ORDER_SUCCESS.getCode()){
                            order.setStatus(Const.OrderStatus.ORDER_CLOSE.getCode());
                            order.setSendTime(new Date());
                            orderMapper.updateByPrimaryKeySelective(order);
                        }
                    }
                }
                return ServerResponse.createBySuccess();
            }
        } catch (Exception ex) {
            return ServerResponse.createByErrorMessage("插入出现异常，请联系管理员。");
        }
        return ServerResponse.createByErrorMessage("插入失败，请稍后重试，或联系管理员。");
    }

    //查询商品的评论信息 带过滤
    @Override
    public ServerResponse<PageInfo> queryAppraiseByQueryModel(AppraiseQueryModel queryModel,int pageNum,int pageSize) {
        try {
            List<Appraise> appraiseList = appraiseMapper.selectByQueryModel(queryModel.getProductId(), queryModel.getOrderId(), queryModel.getGrade());
            List<AppraiseVo> appraiseVos = new ArrayList<>();
            for (Appraise appraise: appraiseList) {
                AppraiseVo appraiseVo = new AppraiseVo();
                String dateString = DateTimeUtil.dateToStr(appraise.getCreateTime());
                appraiseVo.setCreateTime(dateString);
                appraiseVo.setGrade(appraise.getGrade());
                appraiseVo.setProductId(appraise.getProductId());
                appraiseVo.setText(appraise.getText());
                appraiseVos.add(appraiseVo);
            }
            PageHelper.startPage(pageNum, pageSize);
            PageInfo pageInfo = new PageInfo(appraiseVos);
            return ServerResponse.createBySuccess(pageInfo);
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
