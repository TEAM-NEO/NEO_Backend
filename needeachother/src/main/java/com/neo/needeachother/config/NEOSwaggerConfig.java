package com.neo.needeachother.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NEOSwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springDocVersion) {
        Info info = new Info()
                .title("NEO : Need Each Other")
                .version(springDocVersion)
                .description("스타와 팬을 서로 이어주는 서비스, 네오의 API입니다.");

        return new OpenAPI()
                .info(info);
    }

}
