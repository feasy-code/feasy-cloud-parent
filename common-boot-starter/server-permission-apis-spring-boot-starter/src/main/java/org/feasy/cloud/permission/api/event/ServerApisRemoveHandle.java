package org.feasy.cloud.permission.api.event;

import org.feasy.cloud.permission.api.entity.ServerApis;

/**
 * <p>
 *  服务删除APis
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/28
 */
@FunctionalInterface
public interface ServerApisRemoveHandle {
    void handle(ServerApis serverApis);
}
