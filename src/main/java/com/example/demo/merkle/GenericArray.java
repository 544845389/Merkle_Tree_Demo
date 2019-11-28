package com.example.demo.merkle;

import java.util.Iterator;

/**
 * @author 侯存路
 * @date 2019/11/26
 * @company codingApi
 * @description
 */
public class GenericArray<E> implements Iterable<E> {
	private Object[] elements;

	public void append(E... es) {
		elements = ArraysUtil.append(elements, es);
	}

	public int length() {
		if (elements == null)
			return 0;
		return elements.length;
	}

	public E get(int pos) {
		return (E) elements[pos];
	}

	public E last() {
		return (E) get(this.length() - 1);
	}

	// the ease of using for.each statement
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int position = 0;
			private final int limit = length();

			@Override
			public boolean hasNext() {
				return position < limit;
			}

			@Override
			public E next() {
				E e = get(position++);
				// return (E) get(position++);
				return e;
			}

		};
	}

}
