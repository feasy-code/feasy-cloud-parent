package org.feasy.cloud.common.tree;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 树结构数据创建工具类
 *
 * @author yangxiaohui
 * @since 2020/06/28
 */
@Slf4j
public class TreeDataUtil<T extends BaseTreeDTO<T>> {

    public static <T extends BaseTreeDTO> List<T> listToTree(List<T> list){
        return listToTree(list,"0");
    }

    public static <T extends BaseTreeDTO> List<T> listToTree(List<T> list, Object rootParentId){
        // 构造根节点
        List<T> treeList = list.stream().filter(node -> ( rootParentId.equals(node.getParentId()))).collect(Collectors.toList());
        // 删除根节点数据 提升后续的处理效率
        list.removeAll(treeList);
        // 处理子节点数据
        try {
            joinTreeChildNode(treeList, list);
        }catch (Exception e){
            log.error("子节点数据解析错误！", e);
        }
        return treeList;
    }

    private static  <T extends BaseTreeDTO> void joinTreeChildNode(List<T> rootTreeList, List<T> list){
        for (T parentNode : rootTreeList) {
            // 找到当前父节点的子节点列表
            List<T> children = getChildren(parentNode, list);
            if (children.isEmpty()) {
                continue;
            }
            // 将当前父节点的子节点从原始list移除，减少下次处理数据
            list.removeAll(children);
            // 开始下次递归
            joinTreeChildNode(children, list);
        }
    }


    private static <T extends BaseTreeDTO> List<T> getChildren(T parentNode, List<T> list){
        // 找到当前父节点下的子节点列表
        List<T> childNodeList = list.stream().filter(node -> node.getParentId().equals(parentNode.getId())).collect(Collectors.toList());
        if (childNodeList.size()>0){
            parentNode.setHasChildren(true);
            parentNode.setChildren(childNodeList);
        }
        return childNodeList;
    }
}
