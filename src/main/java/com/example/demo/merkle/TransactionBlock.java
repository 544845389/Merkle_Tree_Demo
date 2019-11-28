package com.example.demo.merkle;





import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class TransactionBlock {

    private byte[] transaction;


    public TransactionBlock(byte[] transaction) {
        super();
        this.transaction = transaction;
    }

    public byte[] hash() {
        return DigestUtils.sha256(transaction);
    }

    public boolean equals(TransactionBlock content) {
        return Arrays.equals(this.hash(), content.hash());
    }


}
