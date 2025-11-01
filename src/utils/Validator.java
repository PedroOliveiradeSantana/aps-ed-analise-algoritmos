package utils;

public class Validator {

	private Validator() { }

	public static boolean isSorted(int[] a) {
		if (a == null || a.length < 2) return true;
		for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
		return true;
	}
}