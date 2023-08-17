package com.neo.needeachother.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.neo.needeachother.common.filter.NEOResponseJsonFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NEOJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("NEOResponseJsonFilter", new NEOResponseJsonFilter());
        objectMapper.setFilterProvider(filters);
        return objectMapper;
    }

}
