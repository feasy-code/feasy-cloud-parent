package org.feasy.cloud.permission.api.event;

import org.feasy.cloud.permission.api.entity.ServerApis;
import org.springframework.context.ApplicationListener;

/**
 * <p>
 *  服务API增加监听器
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
public class ServerApisAddListener implements ApplicationListener<ServerApisAddEvent> {

    private final ServerApisAddHandle serverApisAddHandle;
    public ServerApisAddListener(ServerApisAddHandle serverApisAddHandle){
        this.serverApisAddHandle=serverApisAddHandle;
    }
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ServerApisAddEvent event) {
        this.serverApisAddHandle.handle((ServerApis) event.getSource());
    }
}
