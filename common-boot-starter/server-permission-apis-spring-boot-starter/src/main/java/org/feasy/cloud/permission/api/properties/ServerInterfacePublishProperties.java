package org.feasy.cloud.permission.api.properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *  服务发布配置
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@ConfigurationProperties(prefix = "server.interface.publish")
public class ServerInterfacePublishProperties {
    private boolean enable=true;
    private String[] interfacePackage={"org.feasy.cloud.**.controller"};
    private String serverId;
    private String serverName;
    @Value("${spring.application.name}")
    private String applicationName;


    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String[] getInterfacePackage() {
        return interfacePackage;
    }

    public void setInterfacePackage(String[] interfacePackage) {
        this.interfacePackage = interfacePackage;
    }

    public String getServerId() {
        return StringUtils.isEmpty(serverId)?this.applicationName:serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return StringUtils.isEmpty(serverName)?this.applicationName:serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
