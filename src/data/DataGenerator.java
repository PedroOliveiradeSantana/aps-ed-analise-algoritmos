package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DataGenerator {

    private static final int MAX_VALUE = 1_000_000;
    private static final Random RANDOM = new Random();

    private DataGenerator() {}

    public static int[] generateRandomArray(int size) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) a[i] = RANDOM.nextInt(MAX_VALUE);
        return a;
    }

    public static int[] generateSortedArray(int size) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) a[i] = i;
        return a;
    }

    public static int[] generatePartiallySortedArray(int size) {
        int[] a = generateSortedArray(size);
        int swaps = Math.max(1, size / 5);
        for (int i = 0; i < swaps; i++) {
            int p = RANDOM.nextInt(size);
            int q = RANDOM.nextInt(size);
            int tmp = a[p];
            a[p] = a[q];
            a[q] = tmp;
        }
        return a;
    }

    public static int[] copyArray(int[] src) {
        return Arrays.copyOf(src, src.length);
    }

    public static int[] loadFromFile(String path) {
        ArrayList<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        list.add(Integer.parseInt(token));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
