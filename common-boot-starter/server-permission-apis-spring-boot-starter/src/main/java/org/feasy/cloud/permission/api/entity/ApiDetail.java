package org.feasy.cloud.permission.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  API详情表
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiDetail implements Serializable, Comparable<ApiDetail> {
    private static final long serialVersionUID = 1L;
    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口地址
     */
    private String apiPath;

    /**
     * 服务ID+接口地址Hash
     */
    private String apiHash;

    /**
     * 接口-服务ID
     */
    private String serverId;

    /**
     * 接口-服务名
     */
    private String serverName;

    /**
     * 权限标识识别
     */
    private String permissionKeys;

    /**
     * 接口类型
     */
    private String apiType;


    @Override
    public int compareTo(ApiDetail o) {
        return (this.serverId+apiPath+permissionKeys).compareTo(o.getServerId()+o.getApiPath()+o.getPermissionKeys());
    }
}
