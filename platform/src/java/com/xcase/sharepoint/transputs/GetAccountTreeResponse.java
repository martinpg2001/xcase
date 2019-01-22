/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author martin
 */
public interface GetAccountTreeResponse extends SharepointResponse {

    /**
     * @return the tree
     */
    public DefaultMutableTreeNode getTree();

    /**
     * @param tree the tree to set
     */
    public void setTree(DefaultMutableTreeNode tree);

    /**
     * @return the encodedTree
     */
    public String getEncodedTree();

    /**
     * @param encodedTree the encodedTree to set
     */
    public void setEncodedTree(String encodedTree);
}
