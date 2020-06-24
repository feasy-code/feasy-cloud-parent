package org.feasy.cloud.permission.api.event;

import org.feasy.cloud.permission.api.entity.ServerApis;

/**
 * <p>
 *  服务增加APis
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
@FunctionalInterface
public interface ServerApisAddHandle {
    void handle(ServerApis serverApis);
}
