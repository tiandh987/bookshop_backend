package com.bookshop;

import com.bookshop.controller.common.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Auther: lenovo
 * @Date: 2019/3/29 14:22
 * @Description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/manage/**");
    }
}
