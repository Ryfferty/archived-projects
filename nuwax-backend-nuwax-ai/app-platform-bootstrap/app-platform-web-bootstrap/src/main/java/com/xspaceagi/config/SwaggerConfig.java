//package com.xspaceagi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//@EnableSwagger
//public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApiOpRecord() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("接口文档").useDefaultResponseMessages(false).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("com.xspaceagi.agent.web.ui")).paths(PathSelectors.any()).build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("应用发布页接口文档v1").description("接口文档").version("v1").build();
//    }
//}