package org.feasy.cloud.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.reflect.FieldUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 树形结构数据构建工具类
 * </p>
 * @deprecated 该工具已过期，推荐使用{@link org.feasy.cloud.common.tree.TreeDataUtil}，是其的优化版本
 * @author yangxiaohui
 * @since 2020/5/13
 */
@Slf4j
@Deprecated
public class TreeTableUtil {
    public static <T> List<T> listToTreeList(List<T> originalList, String idFieldName, String pidFieldName, String childrenFieldName) {
        // 获取根节点数据
        List<T> rootTreeNodeList = new ArrayList<>();
        rootTreeNodeList.addAll(originalList.stream().filter(original -> {
            try {
                return FieldUtils.getDeclaredField(original.getClass(),pidFieldName,true).get(original).toString().equals("0");
            } catch (Exception e) {
                log.error("根节点数据解析错误！", e);
                return false;
            }
        }).collect(Collectors.toList()));
        // 删除根节点数据 提升后续的处理效率
        originalList.removeAll(rootTreeNodeList);
        // 处理子节点数据
        try {
            packTree(rootTreeNodeList, originalList, idFieldName, pidFieldName, childrenFieldName);
        }catch (Exception e){
            log.error("子节点数据解析错误！", e);
        }
        return rootTreeNodeList;
    }

    private static <T> void packTree(List<T> parentNodeList, List<T> originalList, String keyName,
                                     String pidFieldName, String childrenFieldName) throws Exception {
        for (T parentNode : parentNodeList) {
            // 找到当前父节点的子节点列表
            List<T> children = packChildren(parentNode, originalList, keyName, pidFieldName, childrenFieldName);
            if (children.isEmpty()) {
                continue;
            }
            // 将当前父节点的子节点从原始list移除，减少下次处理数据
            originalList.removeAll(children);
            // 开始下次递归
            packTree(children, originalList, keyName, pidFieldName, childrenFieldName);
        }
    }

    private static <T> List<T> packChildren(T parentNode, List<T> originalList, String keyName, String pidFieldName, String childrenFieldName)throws Exception{
        // 找到当前父节点下的子节点列表
        List<T> childNodeList = new ArrayList<>();
        // 获取ParentId
        String parentId = FieldUtils.getDeclaredField(parentNode.getClass(),keyName,true).get(parentNode).toString();
        childNodeList.addAll(originalList.stream().filter(original -> {
            try {
                return FieldUtils.getDeclaredField(original.getClass(),pidFieldName,true).get(original).toString().equals(parentId);
            } catch (Exception e) {
                log.error("数据解析错误！", e);
                return false;
            }
        }).collect(Collectors.toList()));
        // 将当前父节点下的子节点列表写入到当前父节点下（给子节点列表字段赋值）
        if (!childNodeList.isEmpty()){
            FieldUtils.writeDeclaredField(parentNode, childrenFieldName, childNodeList, true);
        }
        return childNodeList;
    }


}
