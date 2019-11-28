package com.example.demo.merkle;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class Node {
    private Node parent;
    private Node left;
    private Node right;

    private boolean leaf;
    private TransactionBlock content;
    private byte[] hash;
    private boolean dup;

    // walks down the merkle tree until hitting a leaf
    public byte[] verify() {
        if (leaf) {
            return content.hash();
        }

        byte[] rBytes = right.verify();
        byte[] lBytes = left.verify();

        byte[] chash = ByteUtils.merge(rBytes, lBytes);

        return DigestUtils.sha256(chash);
    }

    public Node getParent() {
        return parent;
    }

    public Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Node getLeft() {
        return left;
    }

    public Node setLeft(Node left) {
        this.left = left;
        return this;
    }

    public Node getRight() {
        return right;
    }

    public Node setRight(Node right) {
        this.right = right;
        return this;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public Node setLeaf(boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public TransactionBlock getContent() {
        return content;
    }

    public Node setContent(TransactionBlock content) {
        this.content = content;
        return this;
    }

    public byte[] getHash() {
        return hash;
    }

    public Node setHash(byte[] hash) {
        this.hash = hash;
        return this;
    }

    public boolean isDup() {
        return dup;
    }

    public Node setDup(boolean dup) {
        this.dup = dup;
        return this;
    }

}
