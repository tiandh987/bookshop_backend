package com.bookshop.controller.backend;

import com.bookshop.common.ServerResponse;
import com.bookshop.service.IOrderService;
import com.bookshop.service.IUserService;
import com.bookshop.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;


    //查看订单列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest httpServletRequest,
                                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        //全部通过拦截器验证，已登录管理员账号
        return iOrderService.manageList(pageNum,pageSize);
    }

    //查看订单详情
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> detail(HttpServletRequest httpServletRequest, Long orderNo){

        //全部通过拦截器验证，已登录管理员账号
        return iOrderService.manageDetail(orderNo);
    }

    //查找订单
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest httpServletRequest, Long orderNo,
                                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        //全部通过拦截器验证，已登录管理员账号
        return iOrderService.manageSearch(orderNo,pageNum,pageSize);
    }

    //发货
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest httpServletRequest, Long orderNo){

        //全部通过拦截器验证，已登录管理员账号
        return iOrderService.manageSendGoods(orderNo);
    }

}
