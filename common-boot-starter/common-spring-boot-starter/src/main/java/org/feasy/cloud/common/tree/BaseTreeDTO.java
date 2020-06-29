package org.feasy.cloud.common.tree;

import lombok.Data;

import java.util.List;

/**
 * 树结构拓展类 公共父类
 * @author yangxiaohui
 * @since 2020/06/28
 */
@Data
public class BaseTreeDTO<T> {
    private String id;
    /**
     * 父级ID
     */
    private String parentId;
    /**
     * 是否包含子集节点
     */
    private Boolean hasChildren;
    /**
     * 子集节点
     */
    private List<T> children;
}
