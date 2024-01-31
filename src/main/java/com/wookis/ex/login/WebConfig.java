package com.wookis.ex.login;

import com.wookis.ex.login.web.filter.LogFilter;
import com.wookis.ex.login.web.filter.LoginCheckFilter;
import com.wookis.ex.login.web.interceptor.LogInterceptor;
import com.wookis.ex.login.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/login/**")
                .excludePathPatterns("/css/**","/*.ico","/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/login/**")
                .excludePathPatterns("/login","/login/members/add","/login/sign-in",
                        "/login/logout", "/css/**","/*.ico","/error");
    }



//    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/login/*");    //모든 URL

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean<Filter> loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/login/*");    //모든 URL

        return filterRegistrationBean;
    }
}
