package org.feasy.cloud.permission.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@ConfigurationProperties(prefix = "server.interface.subscribe")
public class ServerInterfaceSubscribeProperties {
    private boolean enable=false;
}
