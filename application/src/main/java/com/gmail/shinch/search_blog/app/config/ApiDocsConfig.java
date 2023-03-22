package com.gmail.shinch.search_blog.app.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApiDocsConfig {
    @Bean
    public OpenAPI handlerOpenApi() {
        return new OpenAPI()
                .info(new Info().title("카카오 뱅크 사전 과제 APIs")
                        .description("카카오 뱅크 입사 사전 과제 Api 입니다.")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("shinch@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("사전 과제 설명서")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
