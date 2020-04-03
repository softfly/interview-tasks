package com.zopa.borrowercalc.commons.utils;

public class NumberUtil {

	protected NumberUtil() {
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
