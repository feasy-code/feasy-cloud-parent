package org.feasy.cloud.permission.api.conf;

import org.feasy.cloud.permission.api.entity.ServerApis;
import org.feasy.cloud.permission.api.global.ServerApisContainer;
import org.feasy.cloud.permission.api.properties.ServerInterfacePublishProperties;
import org.feasy.cloud.permission.api.util.ServerApisBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 *  服务接口发布配置
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Configuration
@EnableConfigurationProperties(ServerInterfacePublishProperties.class)
@ConditionalOnProperty(prefix = "server.interface.publish",name = "enable",havingValue = "true", matchIfMissing = true)
public class ServerInterfacePublishConfiguration {
    @Bean
    public ServerApis serverApis(WebApplicationContext applicationContext, ServerInterfacePublishProperties publishProperties){
        return ServerApisBuilder.builderServerApis(applicationContext,publishProperties);
    }
    @Bean
    @Primary
    @ConditionalOnBean(ServerApis.class)
    public ServerApisContainer serverApisContainer(ApplicationEventPublisher publisher, ServerApis serverApis) {
        return new ServerApisContainer(publisher,serverApis);
    }
}
