//package com.xspaceagi.system.application.converter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import com.xspaceagi.system.application.dto.permission.SysGroupDto;
//
///**
// * 用户组树工具类
// */
//public class GroupTreeUtil {
//
//    /**
//     * 构建用户组树形结构
//     */
//    public static List<SysGroupDto> buildGroupTree(List<SysGroupDto> groupList) {
//        if (CollectionUtils.isEmpty(groupList)) {
//            return new ArrayList<>();
//        }
//
//        // 按ID建立索引，方便查找
//        Map<Long, SysGroupDto> groupMap = groupList.stream()
//                .collect(Collectors.toMap(SysGroupDto::getId, group -> group));
//
//        // 构建树形结构
//        List<SysGroupDto> rootGroups = new ArrayList<>();
//        for (SysGroupDto group : groupList) {
//            Long parentId = group.getParentId();
//            if (parentId == null || parentId == 0L) {
//                // 根节点
//                rootGroups.add(group);
//            } else {
//                // 子节点，添加到父节点的children中
//                SysGroupDto parent = groupMap.get(parentId);
//                if (parent != null) {
//                    if (parent.getChildren() == null) {
//                        parent.setChildren(new ArrayList<>());
//                    }
//                    parent.getChildren().add(group);
//                }
//            }
//        }
//
//        // 对每个节点的children进行排序
//        sortGroupTree(rootGroups);
//
//        // 返回根节点列表（已排序）
//        return rootGroups;
//    }
//
//    /**
//     * 递归排序用户组树
//     */
//    private static void sortGroupTree(List<SysGroupDto> groupList) {
//        if (CollectionUtils.isEmpty(groupList)) {
//            return;
//        }
//        groupList.sort((a, b) -> {
//            Integer sortA = a.getSortIndex() != null ? a.getSortIndex() : 0;
//            Integer sortB = b.getSortIndex() != null ? b.getSortIndex() : 0;
//            return sortA.compareTo(sortB);
//        });
//        for (SysGroupDto group : groupList) {
//            if (CollectionUtils.isNotEmpty(group.getChildren())) {
//                sortGroupTree(group.getChildren());
//            }
//        }
//    }
//}
//
