package com.neo.needeachother.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neo.needeachother.common.filter.NEOResponseJsonFilter;
import com.neo.needeachother.common.service.NEOInterceptorService;
import com.neo.needeachother.users.filter.NEOInfoDtoJsonFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@TestConfiguration
public class NEOTestConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("NEOResponseJsonFilter", new NEOResponseJsonFilter())
                .addFilter("NEOInfoDtoJsonFilter", new NEOInfoDtoJsonFilter());
        objectMapper.setFilterProvider(filters);
        return objectMapper;
    }

    @Bean
    public NEOInterceptorService getNEOInterceptorService(){
        return new NEOInterceptorService(objectMapper());
    }

    @Bean
    MockMvcBuilderCustomizer utf8Config() {
        return builder ->
                builder.addFilters(new CharacterEncodingFilter("UTF-8", true));
    }

}
