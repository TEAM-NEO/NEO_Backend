package com.neo.needeachother.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neo.needeachother.common.filter.NEOResponseJsonFilter;
import com.neo.needeachother.users.filter.NEOInfoDtoJsonFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * JSON 직렬화를 위한 Jackson에 대한 설정을 진행합니다.<br>
 * 주로 {@code @JsonFilter}나 {@code LocalDateTime}를 생성/등록하기 위한 로직이 포함됩니다.<br>
 */
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
