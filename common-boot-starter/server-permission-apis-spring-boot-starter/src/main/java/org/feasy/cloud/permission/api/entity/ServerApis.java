package org.feasy.cloud.permission.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 接口信息
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServerApis implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 服务ID
     */
    private String serverId;
    /**
     * 服务名
     */
    private String serverName;
    /**
     * 服务API版本
     */
    private String serverApiVersion;

    /**
     * 接口信息集合
     */
    private List<ApiDetail> apis = new ArrayList<>();
}
