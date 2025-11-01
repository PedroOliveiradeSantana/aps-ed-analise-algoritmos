package utils;

public class Statistics {
    private Statistics() {}

    public static double mean(long[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (long v : values) sum += v;
        return sum / values.length;
    }

    public static double stddev(long[] values) {
        if (values == null || values.length <= 1) return 0.0;
        double mean = mean(values);
        double s = 0.0;
        for (long v : values) {
            double d = v - mean;
            s += d * d;
        }
        
        return Math.sqrt(s / (values.length - 1));
    }
}