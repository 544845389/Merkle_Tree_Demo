package com.example.demo;

import com.example.demo.merkle.ByteUtils;
import com.example.demo.merkle.MerklePath;
import com.example.demo.merkle.MerkleTree;
import com.example.demo.merkle.TransactionBlock;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class Test {



    public static void main(String[] args) {

        String str1 = "哈哈";
        String str2 = "呵呵";
        String str3 = "嘻嘻";
        String str4 = "嘤嘤";

        System.out.println("str1 sha256：" + Hex.encodeHexString(DigestUtils.sha256(str1.getBytes())));
        System.out.println("str2 sha256：" + Hex.encodeHexString(DigestUtils.sha256(str2.getBytes())));
        System.out.println("str3 sha256：" + Hex.encodeHexString(DigestUtils.sha256(str3.getBytes())));
        System.out.println("str4 sha256：" + Hex.encodeHexString(DigestUtils.sha256(str4.getBytes())));


        List<TransactionBlock> transactionBlocks = new ArrayList<>();
        transactionBlocks.add(new TransactionBlock(str1.getBytes()));
        transactionBlocks.add(new TransactionBlock(str2.getBytes()));
        transactionBlocks.add(new TransactionBlock(str3.getBytes()));
        transactionBlocks.add(new TransactionBlock(str4.getBytes()));

        TransactionBlock[] transactionBlock = new TransactionBlock[transactionBlocks.size()];
        transactionBlocks.toArray(transactionBlock);

        MerkleTree merkleTree = MerkleTree.create(transactionBlock);

        System.out.println("正常计算：" + Hex.encodeHexString(merkleTree.getRoot().getHash()));

        byte[] str12 = DigestUtils.sha256(ByteUtils.merge(DigestUtils.sha256(str1.getBytes()), DigestUtils.sha256(str2.getBytes())));
        byte[] str34 = DigestUtils.sha256(ByteUtils.merge(DigestUtils.sha256(str3.getBytes()), DigestUtils.sha256(str4.getBytes())));
        byte[] str1234 = DigestUtils.sha256(ByteUtils.merge(str12, str34));
        System.out.println("手动计算str12：" + Hex.encodeHexString(str12));
        System.out.println("手动计算str34：" + Hex.encodeHexString(str34));
        System.out.println("手动计算str1234：" + Hex.encodeHexString(str1234));


        MerklePath merklePath = merkleTree.getMerklePath(new TransactionBlock(str2.getBytes()));
        for (byte[] bytes : merklePath.path()) {
            System.out.println("树路径：" + Hex.encodeHexString(bytes));
        }


        System.out.println("重新计算：" + Hex.encodeHexString(merklePath.recalculateRoot(DigestUtils.sha256(str2.getBytes()))));
    }


}
