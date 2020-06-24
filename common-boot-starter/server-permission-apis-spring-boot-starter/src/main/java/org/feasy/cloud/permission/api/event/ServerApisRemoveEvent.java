package org.feasy.cloud.permission.api.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *  服务API删除事件
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
public class ServerApisRemoveEvent  extends ApplicationEvent {

    public ServerApisRemoveEvent(Object source) {
        super(source);
    }
}
