/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.transputs.GetAccountTreeResponse;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author martin
 *
 */
public class GetAccountTreeResponseImpl extends SharepointResponseImpl implements GetAccountTreeResponse {

    /**
     * a Java Swing tree structure, and each of tree node attached a
     * BoxAbstractFile object, which can be either file or folder.
     */
    private DefaultMutableTreeNode tree;

    /**
     * base64 encoded string of XML content.
     */
    private String encodedTree;

    /**
     * @return the tree
     */
    public DefaultMutableTreeNode getTree() {
        return this.tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(DefaultMutableTreeNode tree) {
        this.tree = tree;
    }

    /**
     * @return the encodedTree
     */
    public String getEncodedTree() {
        return this.encodedTree;
    }

    /**
     * @param encodedTree the encodedTree to set
     */
    public void setEncodedTree(String encodedTree) {
        this.encodedTree = encodedTree;
    }
}
