package org.feasy.cloud.permission.api.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *  服务API增加事件
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
public class ServerApisAddEvent extends ApplicationEvent {

    public ServerApisAddEvent(Object source) {
        super(source);
    }
}
