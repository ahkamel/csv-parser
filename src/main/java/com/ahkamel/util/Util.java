package com.ahkamel.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

	// TODO: This is very risk, needs enhancement and using decimal formatter
	public static Long getCentAmount(String input) {
		return Long.valueOf(input.split("\\.")[1]);
	}

	public static <T> List<T> createBatchList(List<T> list) {
		List<T> newList = new ArrayList<>(list);
		return newList;
	}
}
