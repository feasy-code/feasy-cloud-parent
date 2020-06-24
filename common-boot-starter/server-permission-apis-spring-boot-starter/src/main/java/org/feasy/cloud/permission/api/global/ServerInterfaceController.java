package org.feasy.cloud.permission.api.global;

import org.feasy.cloud.permission.api.entity.ServerApis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@RestController
@ConditionalOnProperty(prefix = "server.interface.publish",name = "enable",havingValue = "true", matchIfMissing = true)
public class ServerInterfaceController{
    private final ServerApis serverApis;
    public ServerInterfaceController(ServerApis serverApis) {
        this.serverApis = serverApis;
    }
    @GetMapping("/serverInterface/serverApiDetails")
    public ServerApis serverApiDetails() {
        return serverApis;
    }
}
