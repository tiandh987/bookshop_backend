package com.bookshop.controller.common;


import com.bookshop.common.Const;
import com.bookshop.pojo.User;
import com.bookshop.util.CookieUtil;
import com.bookshop.util.JsonUtil;
import com.bookshop.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求拦截器：当用户向服务端发送不同请求时，重置Redis中存储的session的有效期
 */
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
@Slf4j
public class SessionExpireFilter implements Filter {
    //注意：导入的是javax.servlet.Filter包
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter--SessionExpireFilter");
        //将ServletRequest强转为HttpServletRequest
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String loginToken = CookieUtil.readLoginCookie(httpServletRequest);

        if (StringUtils.isNotEmpty(loginToken)) {
            //判断loginToken是否为空或""
            //如果不为空，符合条件，继续拿User信息
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            User user = JsonUtil.stringToObject(userJsonStr, User.class);
            if (user != null) {
                //use不为空，则重置session的时间
                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
