package com.lx.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: JUnit3 Test Class.java.java
 * @Package: gov.mof.fasp2.gfbi.common.impl
 * @Description: 测试没有父类的数据构建成树型结构，并增加children
 * @author: zhaozhiwei
 * @date: 2022/11/18 下午5:43
 * @version: V1.0
 */
public class TreeBuildNoParentId {

    class TreeNode {
        private String id;
        private String levelNo;

        private String pid;

        private List<TreeNode> children;

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        public TreeNode(String id, String levelNo) {
            this.id = id;
            this.levelNo = levelNo;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevelNo() {
            return levelNo;
        }

        public void setLevelNo(String levelNo) {
            this.levelNo = levelNo;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "id='" + id + '\'' +
                    ", levelNo='" + levelNo + '\'' +
                    ", pid='" + pid + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    public void buildTree() {
        // 1. 搞一份数据
        final List<TreeNode> items = Arrays.asList(
                new TreeNode("001", "1"),
                new TreeNode("001001", "2"),
                new TreeNode("001001001", "3"),
                new TreeNode("001001001001", "4"),
                new TreeNode("001001001002", "4"),
                new TreeNode("001001001003", "4"),
                new TreeNode("001001002", "3"),
                new TreeNode("001001003", "3"),
                new TreeNode("001001003001", "4"),
                new TreeNode("001001003002", "4"),
                new TreeNode("001002", "2"),
                new TreeNode("001002001", "3"),
                new TreeNode("002", "1"),
                new TreeNode("002001", "2"),
                new TreeNode("002001001", "3")
        );

        // 构建成树
        final List<TreeNode> collect = new ArrayList<>(items);
        final List<TreeNode> treeNodes = buildTree(collect);
        System.out.println(treeNodes);
    }

    public List<TreeNode> buildTree(List<TreeNode> treeNodes) {

        final List<TreeNode> itemsExistsParent = new ArrayList<>();
        // 填充父id
        for (TreeNode map : treeNodes) {
            final String levelNo = map.getLevelNo();
            if ("1".equals(levelNo)) {
                map.setPid("0");
                itemsExistsParent.add(map);
            } else {
                // 倒序找当前最后一个级次比它小的
                for (int i = itemsExistsParent.size() - 1; i > -1; i--) {
                    final TreeNode treeNode = itemsExistsParent.get(i);
                    if (Integer.parseInt(String.valueOf(treeNode.getLevelNo())) < Integer.parseInt(levelNo)) {
                        map.setPid(treeNode.getId());
                        itemsExistsParent.add(map);
                        break;
                    }
                }

            }
        }

        // 获取根节点，即找出父节点为空的对象
        List<TreeNode> rootNodeList = new ArrayList<>();
        for (TreeNode t : treeNodes) {
            if (t.getLevelNo().equals("1")) {
                rootNodeList.add(t);
            }
        }

        // 将根节点从原始list移除，减少下次处理数据
        treeNodes.removeAll(rootNodeList);

        // 递归封装树
        packTree(rootNodeList, treeNodes);

        return rootNodeList;
    }

    /**
     * 封装树（向下递归）
     *
     * @param parentNodeList 要封装为树的父节点对象集合
     * @param originalList   原始list数据
     */
    private static void packTree(List<TreeNode> parentNodeList, List<TreeNode> originalList) {
        for (TreeNode parentNode : parentNodeList) {
            // 找到当前父节点的子节点列表
            List<TreeNode> children = packChildren(parentNode, originalList);
            if (children.isEmpty()) {
                continue;
            }
            // 将当前父节点的子节点从原始list移除，减少下次处理数据
            originalList.removeAll(children);
            // 开始下次递归
            packTree(children, originalList);
        }
    }

    /**
     * 封装子对象
     *
     * @param parentNode   父节点对象
     * @param originalList 原始list数据
     */
    private static List<TreeNode> packChildren(TreeNode parentNode, List<TreeNode> originalList) {
        List<TreeNode> childNodeList = new ArrayList<>();
        String parentId = parentNode.getId();
        for (TreeNode t : originalList) {
            String childNodeParentId = t.getPid();
            if (parentId.equals(childNodeParentId)) {
                childNodeList.add(t);
            }
        }
        if (!childNodeList.isEmpty()) {
            parentNode.setChildren(childNodeList);
        }
        return childNodeList;
    }

    public static void main(String[] args) {
        new TreeBuildNoParentId().buildTree();
    }
}