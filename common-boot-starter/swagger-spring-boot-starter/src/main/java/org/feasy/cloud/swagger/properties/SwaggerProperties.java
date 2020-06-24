package org.feasy.cloud.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/25
 */
@ConfigurationProperties(prefix = "swagger.api")
public class SwaggerProperties {
    private boolean enable=true;
    private String packagePath="";

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}
