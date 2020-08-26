package com.zzx.configuration;

import com.zzx.interceptor.LoginRequiredInterceptor;
import com.zzx.interceptor.PassportInterceptor;
import com.zzx.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName ToutiaoWebConfiguration
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/11 0:38
 **/
@Configuration
public class ToutiaoWebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;
    @Autowired
    TokenInterceptor tokenInterceptor;
    String[] url=new String[]{"/reg"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/image/**").addPathPatterns("/admin/**").addPathPatterns("/add/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns(url);
    }


}
