package org.feasy.cloud.permission.api.event;

import org.feasy.cloud.permission.api.entity.ServerApis;
import org.springframework.context.ApplicationListener;

/**
 * <p>
 *  服务APIs删除监听器
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
public class ServerApisRemoveListener implements ApplicationListener<ServerApisRemoveEvent> {

    private final ServerApisRemoveHandle serverApisRemoveHandle;
    public ServerApisRemoveListener(ServerApisRemoveHandle serverApisRemoveHandle){
        this.serverApisRemoveHandle=serverApisRemoveHandle;
    }
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ServerApisRemoveEvent event) {
        this.serverApisRemoveHandle.handle((ServerApis) event.getSource());
    }
}
