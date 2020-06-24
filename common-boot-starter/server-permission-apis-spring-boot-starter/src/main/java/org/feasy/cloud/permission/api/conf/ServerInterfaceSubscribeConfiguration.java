package org.feasy.cloud.permission.api.conf;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import org.feasy.cloud.permission.api.entity.ServerApis;
import org.feasy.cloud.permission.api.event.WatchServerChangedListener;
import org.feasy.cloud.permission.api.event.WatchServerChangedLocator;
import org.feasy.cloud.permission.api.global.ServerApisContainer;
import org.feasy.cloud.permission.api.properties.ServerInterfaceSubscribeProperties;
import org.feasy.cloud.permission.api.util.PermissionServerApisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *  服务接口订阅配置
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Configuration
@EnableConfigurationProperties(ServerInterfaceSubscribeProperties.class)
@ConditionalOnProperty(prefix = "server.interface.subscribe",name = "enable",havingValue = "true",matchIfMissing = true)
public class ServerInterfaceSubscribeConfiguration {
    @Value("${spring.application.name}")
    private String applicationName;


    @Bean
    public WatchServerChangedListener serverChangedListener(ApplicationEventPublisher publisher){
        return new WatchServerChangedListener(publisher);
    }

    @Bean
    public WatchServerChangedLocator serverChangedLocator(NacosServiceDiscovery nacosServiceDiscovery, PermissionServerApisClient serverApisClient){
        return new WatchServerChangedLocator(nacosServiceDiscovery,serverApisClient,applicationName);
    }

    @Bean
    @ConditionalOnMissingBean({ServerApisContainer.class,ServerApis.class})
    public ServerApisContainer serverApisContainer(ApplicationEventPublisher publisher) {
        return new ServerApisContainer(publisher);
    }
}
