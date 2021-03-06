package com.jhzz.jhzzblog.config;

import com.jhzz.jhzzblog.handler.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 14:37
 * \* Description:
 * \
 */
@Configuration
@Slf4j
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置 也可以在类上加注解@CrossOrigin
        registry.addMapping("/**").allowedOrigins("http://121.43.101.99:8777", "http://localhost:8777", "http://121.43.101.99", "http://jhzzmw.top/", "http://jhzzmw.top:8777");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口 后续实际遇到需要拦截的接口时 在配置真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
        ///create/change
    }

}
