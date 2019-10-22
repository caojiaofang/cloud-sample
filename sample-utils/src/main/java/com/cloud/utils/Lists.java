package com.cloud.utils;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	public static <E> ArrayList<E> newArrayList(E... elements) {
		Preconditions.checkNotNull(elements); // for GWT
		// Avoid integer overflow when a large array is passed in
		int capacity = computeArrayListCapacity(elements.length);
		ArrayList<E> list = new ArrayList<E>(capacity);
		Collections.addAll(list, elements);
		return list;
	}

	static int computeArrayListCapacity(int arraySize) {
		Preconditions.checkArgument(arraySize >= 0);
		// TODO(kevinb): Figure out the right behavior, and document it
		return Ints.saturatedCast(5L + arraySize + (arraySize / 10));
	}

}
