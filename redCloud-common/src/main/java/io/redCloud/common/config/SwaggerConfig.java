package io.redCloud.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {


//        ParameterBuilder parameterBuilder=new ParameterBuilder();
//        //设置一个全局的token令牌
//        List<Parameter> parameters= Lists.newArrayList();
//        parameterBuilder.name("token").description("token令牌").modelRef(new ModelRef("String")).parameterType("header").defaultValue("abc").required(true).build();
//        parameters.add(parameterBuilder.build());
/*        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swagger.bootstrap.ui.demo.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(securityContext(),securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(),apiKey1()));*/

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("io.redCloud.modules.mark.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
                /*.globalOperationParameters(parameters)*/;
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("RedCloudTechnology")
                .description("接口文档")
                .termsOfServiceUrl("https://www.jihongyun.cn")
                .contact(new Contact("冀鸿运","https://www.jihongyun.cn","15721460@qq.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> security() {
        return Lists.newArrayList(
                new ApiKey("Authorization", "Authorization", "header"),
                new ApiKey("token", "token", "header")
        );
    }

    private List<SecurityContext> securityContexts() {
        SecurityContext build = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(build);
        return arrayList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        SecurityReference authorization = new SecurityReference("Authorization", authorizationScopes);
        ArrayList arrayList = new ArrayList<>();
        arrayList.add(authorization);
        return arrayList;
    }

}