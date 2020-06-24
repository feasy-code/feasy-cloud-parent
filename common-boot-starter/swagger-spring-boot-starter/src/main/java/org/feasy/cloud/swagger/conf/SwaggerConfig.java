package org.feasy.cloud.swagger.conf;

import org.feasy.cloud.swagger.properties.SwaggerProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Swagger配置类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/23
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger.api.enable", value = "true", matchIfMissing = true)
public class SwaggerConfig {
    private final SwaggerProperties swaggerProperties;

    @Value("${spring.profiles.active}")
    private String profilesActive;
    @Value("${spring.application.name}")
    private String applicationName;

    public SwaggerConfig(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }


    @Bean
    public Docket createRestApi() {
        // 全局响应设置
        List<ResponseMessage> responseMessageList = this.buildResponseMessageList();
        // 是否开启Swagger文档 判断环境是dev或者test开启文档
        boolean isOpenSwagger = this.swaggerProperties.getEnable() && (profilesActive.contains("dev") || profilesActive.contains("test"));
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .enable(isOpenSwagger)
                .apiInfo(new ApiInfoBuilder()
                        .title(applicationName.toUpperCase() + " RestFul Apis")
                        .description(applicationName.toUpperCase() + " RestFul Apis")
                        .termsOfServiceUrl("#")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.getControllerPackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 统一响应状态码 设置
     */
    private List<ResponseMessage> buildResponseMessageList() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder().code(0).message("请求成功").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(-1).message("未知错误").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1001).message("用户请求操作失败").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1002).message("Token非法").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1003).message("无权访问 拒绝请求").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1004).message("未找到请求的资源 无效的资源").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1005).message("请求太过频繁  限流 限制访问").responseModel(new ModelRef("Result")).build());
            add(new ResponseMessageBuilder().code(1006).message("请求被重定向").responseModel(new ModelRef("Result")).build());
        }};
    }

    /**
     * 获取Controller的包名
     */
    private String getControllerPackage() {
        if (StringUtils.isBlank(this.swaggerProperties.getPackagePath())) {
            return "org.feasy.cloud." + applicationName.split("-")[0];
        }
        return this.swaggerProperties.getPackagePath();
    }
}
