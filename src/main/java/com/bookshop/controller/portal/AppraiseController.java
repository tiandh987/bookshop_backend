package com.bookshop.controller.portal;

import com.alipay.api.internal.util.StringUtils;
import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Appraise;
import com.bookshop.pojo.User;
import com.bookshop.service.IAppraiseService;
import com.bookshop.util.CookieUtil;
import com.bookshop.util.JsonUtil;
import com.bookshop.util.RedisShardedPoolUtil;
import com.bookshop.vo.AppraiseQueryModel;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/appraise/")
public class AppraiseController {
    @Autowired
    private IAppraiseService appraiseService;

    @RequestMapping(value = "query_appraise_model.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> QueryAppraiseByQueryModel(HttpServletRequest httpServletRequest, AppraiseQueryModel queryModel, Integer pageNum, Integer pageSize) {
        //从客户端中读取Cookie
        String loginToken = CookieUtil.readLoginCookie(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //从redis中获取User的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.stringToObject(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return appraiseService.queryAppraiseByQueryModel(queryModel, pageNum, pageSize);
    }

    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse CreateAppraise(HttpServletRequest httpServletRequest, Appraise appraise) {
        //从客户端中读取Cookie
        String loginToken = CookieUtil.readLoginCookie(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //从redis中获取User的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.stringToObject(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return appraiseService.saveAppraise(appraise);
    }

    @RequestMapping(value = "query_appraise_orderId.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse HavingAppraiseByOrderId(HttpServletRequest httpServletRequest, int orderId) {
        //从客户端中读取Cookie
        String loginToken = CookieUtil.readLoginCookie(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //从redis中获取User的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.stringToObject(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return appraiseService.havingAppraiseByOrderId(orderId);
    }


}
