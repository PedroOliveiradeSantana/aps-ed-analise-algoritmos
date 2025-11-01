package utils;

public class Timer {

	private Timer() { }

	public static long measureNanos(Runnable action) {
		long start = System.nanoTime();
		action.run();
		long end = System.nanoTime();
		return end - start;
	}
}