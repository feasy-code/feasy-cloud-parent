package org.feasy.cloud.permission.api.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *  服务变化触发事件
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
public class WatchServerChangedEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public WatchServerChangedEvent(Object source) {
        super(source);
    }
}
