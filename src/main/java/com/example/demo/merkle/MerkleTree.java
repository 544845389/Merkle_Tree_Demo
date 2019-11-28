package com.example.demo.merkle;




import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class MerkleTree {
    private Node root;
    private byte[] merkleRoot;
    private GenericArray<Node> leaves;

    public static MerkleTree create(TransactionBlock[] contents) {
        return buildWithContent(contents);
    }

    // mostly build leaves at the bottom and pass leaves into {@code
    // buildWithLeaves}
    private static MerkleTree buildWithContent(TransactionBlock[] contents) {
        if (contents == null || contents.length == 0) {
            throw new IllegalStateException("cannot construct tree with no content");
        }

        GenericArray<Node> leaves = new GenericArray<Node>();
        for (TransactionBlock content : contents) {
            byte[] hash = content.hash();
            Node newNode = new Node();

            leaves.append(newNode.setHash(hash).setContent(content).setLeaf(true));
        }

        if (leaves.length() % 2 == 1) {
            Node duplicate = new Node();
            duplicate.setHash(leaves.last().getHash()).setContent(leaves.last().getContent()).setLeaf(true)
                    .setDup(true);
            leaves.append(duplicate);
        }

        Node root = buildWithLeaves(leaves);
        MerkleTree mt = new MerkleTree();
        mt.root = root;
        mt.merkleRoot = root.getHash();
        mt.leaves = leaves;

        return mt;

    }

    // build inner branches or root with a given list of leaves
    private static Node buildWithLeaves(GenericArray<Node> nl) {
        GenericArray<Node> branches = new GenericArray<>();

        for (int i = 0; i < nl.length(); i += 2) {
            int left = i;
            int right = i + 1;
            if (i + 1 == nl.length()) {
                right = i;
            }

            byte[] chash = ByteUtils.merge(nl.get(left).getHash(), nl.get(right).getHash());
            Node node = new Node();
            node.setLeft(nl.get(left))
                    .setRight(nl.get(right))
                    .setHash(DigestUtils.sha256(chash));

            branches.append(node);
            nl.get(left).setParent(node);
            nl.get(right).setParent(node);

            if (nl.length() == 2) {
                return node;
            }
        }

        return buildWithLeaves(branches);
    }

    public boolean verify() {
        byte[] mr = root.verify();

        return Arrays.equals(merkleRoot, mr);
    }


    public MerklePath getMerklePath(TransactionBlock content) {
        for (Node node : leaves) {
            Node current = node;
            if (current.getContent().equals(content)) {
                Node curParent = current.getParent();
                GenericArray<byte[]> path = new GenericArray<>();
                GenericArray<String> index = new GenericArray<>();

                while (curParent != null) {
                    if (Arrays.equals(curParent.getLeft().getHash(), node.getHash())) {
                        path.append(curParent.getRight().getHash());
                        index.append("r");
                    } else {
                        path.append(curParent.getLeft().getHash());
                        index.append("l");
                    }
                    node = curParent;
                    curParent = curParent.getParent();
                }
                return new MerklePath(path, index);
            }
        }

        return null;
    }

    public Node getRoot() {
        return root;
    }

}
