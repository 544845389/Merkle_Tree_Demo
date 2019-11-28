package com.example.demo.merkle;



import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class MerklePath {
    private GenericArray<byte[]> path;
    private GenericArray<String> index;// "r" or "l"

    public MerklePath(GenericArray<byte[]> path, GenericArray<String> index) {
        super();
        this.path = path;
        this.index = index;
    }

    public MerklePath(byte[][] path, String[] index) {
        super();
        this.path = new GenericArray<byte[]>();
        this.path.append(path);
        this.index = new GenericArray<String>();
        this.index.append(index);
    }

    // core method to validate block which implements {@code INodeContent}
	public boolean validate(TransactionBlock content, byte[] expectedRootHash) {
		byte[] blockHash = content.hash();
		byte[] calculatedRootHash = recalculateRoot(blockHash);

		return Arrays.equals(calculatedRootHash, expectedRootHash);
	}

    public byte[][] path() {
        byte[][] result = new byte[path.length()][];
        int i = 0;

        for (byte[] bytes : path) {
            result[i] = bytes;
            i++;
        }

        return result;
    }

    public String[] index() {
        String[] result = new String[index.length()];
        int i = 0;

        for (String idx : index) {
            result[i] = idx;
            i++;
        }

        return result;

    }

    public byte[] recalculateRoot(byte[] leaf) {
        byte[][] path = path();
        String[] index = index();
        int i = 0;
        byte[] chash = leaf;

        for (byte[] bytes : path) {
            if (index[i].equals("r")) {
                chash = ByteUtils.merge(chash, bytes);
            } else {
                chash = ByteUtils.merge(bytes, chash);
            }
            chash = DigestUtils.sha256(chash);
            i++;
        }
        return chash;
    }

}
