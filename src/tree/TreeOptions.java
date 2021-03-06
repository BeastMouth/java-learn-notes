package tree;

/**
 * @author huang
 * @version 1.0
 * @date 2019/01/07 10:55
 **/
public interface TreeOptions {

    /**
     * 二叉查找树查找节点
     *
     * @param root 根节点
     * @param key  data值
     * @return treeNode
     * @author hbj
     * @date 2019/01/07
     */
    TreeNode find(TreeNode root, int key);

    /**
     * 二叉查找树插入节点
     *
     * @param root 根节点
     * @param key  data值
     * @return boolean
     * @author hbj
     * @date 2019/01/07
     */
    boolean insert(TreeNode root, int key);

    /**
     * 二叉查找树删除节点
     *
     * @param root 根节点
     * @param key  data值
     * @return boolean
     * @author hbj
     * @date 2019/01/07
     */
    void delete(TreeNode root, int key);

    /**
     * 中序遍历
     *
     * @param current 目前节点
     * @author hbj
     * @date 2019/01/07
     */
    void infixOrder(TreeNode current);

    /**
     * 先序遍历
     *
     * @param current 目前节点
     * @author hbj
     * @date 2019/01/07
     */
    void preOrder(TreeNode current);

    /**
     * 后序遍历
     *
     * @param current 目前节点
     * @author hbj
     * @date 2019/01/07
     */
    void postOrder(TreeNode current);

    /**
     * 层级遍历
     *
     * @param root 根节点
     * @author hbj
     * @date 2019/01/07
     */
    void levelOrder(TreeNode root);
}
