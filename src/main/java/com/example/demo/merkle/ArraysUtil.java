package com.example.demo.merkle;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class ArraysUtil {

	public static Object[] append(Object[] array, Object... ts) {
		int fromIndex = 0;
		Object[] newArray;
		if (array == null) {
			array = new Object[ts.length];

			for (int i = 0; i < array.length; i++) {
				array[i] = ts[i];
			}

			return array;
		}

		newArray = new Object[ts.length + array.length];
		System.arraycopy(array, 0, newArray, 0, array.length);
		System.arraycopy(ts, 0, newArray, array.length, ts.length);

		return newArray;
	}

}
