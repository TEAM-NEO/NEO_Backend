package com.neo.needeachother.config;

import com.neo.needeachother.users.filter.NEOUserDomainBadRequestFilter;
import com.neo.needeachother.users.interceptor.NEOUserDomainBadRequestInterceptor;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class NEOWebConfig implements WebMvcConfigurer {

    private final NEOUserDomainBadRequestInterceptor userDomainBadRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userDomainBadRequestInterceptor)
                .addPathPatterns("/api/v1/users/**");
    }

    @Bean
    public FilterRegistrationBean<Filter> addUserDomainBadRequestFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new NEOUserDomainBadRequestFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/api/v1/users/*");

        return bean;
    }

}
