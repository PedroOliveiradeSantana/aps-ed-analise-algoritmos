package algorithms;

import java.util.Random;

public class QuickSort {

    private static final Random RANDOM = new Random();

    private QuickSort() {}

    public static void sort(int[] array) {
        if (array == null || array.length < 2) return;
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int low, int high) {
        if (low >= high) return;

        int pi = randomizedPartition(array, low, high);
        sort(array, low, pi - 1);
        sort(array, pi + 1, high);
    }

    private static int randomizedPartition(int[] array, int low, int high) {
        int pivotIndex = low + RANDOM.nextInt(high - low + 1);
        swap(array, pivotIndex, high);
        return partition(array, low, high);
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}