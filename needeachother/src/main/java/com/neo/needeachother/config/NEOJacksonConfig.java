package com.neo.needeachother.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neo.needeachother.common.filter.NEOResponseJsonFilter;
import com.neo.needeachother.users.filter.NEOInfoDtoJsonFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NEOJacksonConfig {

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

}
