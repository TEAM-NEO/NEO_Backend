package com.neo.needeachother.config;

import com.neo.needeachother.users.interceptor.NEOUserDomainBadRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class NEOWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NEOUserDomainBadRequestInterceptor())
                .addPathPatterns("/your-endpoint-url");
    }

}
