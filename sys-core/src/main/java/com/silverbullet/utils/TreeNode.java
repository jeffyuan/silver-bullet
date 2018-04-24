package com.silverbullet.utils;

import org.apache.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形控件Node数据结构
 * Created by jeffyuan on 2018/3/9.
 */
public class TreeNode implements Serializable{

    private static final long serialVersionUID = 1L;
    static final Logger log = Logger.getLogger(TreeNode.class);

    private String id;
    private String text;
    private String iconCls; //系统图标
    private String state;  //closed或open 表示节点是展开还是折叠
    // 子节点
    private List<TreeNode> children = new ArrayList<TreeNode>();
    // 其他参数
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public TreeNode() {

    }

    public TreeNode(String id, String name, Map<String,Object> params) {
        this.id = id;
        this.text = name;
        this.attributes = params;
    }

    /**
     * 将map中的节点按照树的结构进行格式化
     * @param listMapNodes  需要格式化的数据
     * @param parentIdKeyName parentId的Key的名称
     * @param idKeyName  id的key的名称
     * @param iconClsKeyName 图标key的名称
     * @param childrenNumKeyName 拥有children的名称
     * @return
     */
    public static List<TreeNode> formatNodes2TreeNode(List<Map<String, Object>> listMapNodes, String nameKeyName,
                                               String parentIdKeyName, String idKeyName,
                                                      String iconClsKeyName, String childrenNumKeyName) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();

        for (Map<String, Object> node : listMapNodes) {
            if (!node.containsKey(idKeyName)) {
                continue;
            }

            //创建新节点
            TreeNode treeNode = new TreeNode(String.valueOf(node.get(idKeyName)),
                    String.valueOf(node.get(nameKeyName)), node );

            // 设置图标
            if (iconClsKeyName != null && iconClsKeyName.length() > 0) {
                treeNode.setIconCls(String.valueOf(node.get(iconClsKeyName)));
            }

            // 设置是否有子叶
            if (childrenNumKeyName != null && childrenNumKeyName.length() > 0) {
                long nNode = 0;
                try {
                    nNode = Long.valueOf(String.valueOf(node.get(childrenNumKeyName)));
                } catch (Exception e) { e.printStackTrace();}

                if (nNode > 0) {
                    treeNode.setState("closed");
                } else {
                    treeNode.setState("open");
                }
            }

            // 查找新节点父位置，并插入
            treeNodes = addNodeInit(treeNodes, String.valueOf(node.get(parentIdKeyName)), treeNode);

        }

        return treeNodes;
    }

    /**
     * 在树节点中查找节点
     * @param nodes 需要查找的集合
     * @param id 节点的id
     * @return
     */
    public static TreeNode findNode(List<TreeNode> nodes, String id) {

        for (TreeNode node : nodes) {
            if (node.id.equals(id)) {
                return node;
            } else {
                TreeNode nodeFind = findNode(node.children, id);
                if (nodeFind != null) {
                    return nodeFind;
                }
            }
        }

        return null;
    }

    /**
     * 增加节点
     * @param nodes 完整的树节点
     * @param parentId 父节点ID
     * @param newTreeNode 新的节点
     */
    public static List<TreeNode> addNodeInit(List<TreeNode> nodes, String parentId, TreeNode newTreeNode) {
        if (nodes == null) {
            nodes = new ArrayList<TreeNode>();
        }

        TreeNode parentNode = findNode(nodes, parentId);
        if (parentNode == null) {
            nodes.add(newTreeNode);
        } else {
            parentNode.addNode(newTreeNode);
        }

        return nodes;
    }


    /**
     * 添加子节点
     * @param treeNode
     */
    public void addNode(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
