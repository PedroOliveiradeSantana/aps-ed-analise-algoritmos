package algorithms;

public class MergeSort {

	private MergeSort() {}

	public static void sort(int[] array) {
		if (array == null || array.length < 2) return;
		sort(array, 0, array.length - 1);
	}

	private static void sort(int[] array, int left, int right) {
		if (left >= right) return;
		int mid = left + (right - left) / 2;
		sort(array, left, mid);
		sort(array, mid + 1, right);
		merge(array, left, mid, right);
	}

	private static void merge(int[] array, int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;

		int[] L = new int[n1];
		int[] R = new int[n2];

		for (int i = 0; i < n1; i++) L[i] = array[left + i];
		for (int j = 0; j < n2; j++) R[j] = array[mid + 1 + j];

		int i = 0, j = 0, k = left;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) array[k++] = L[i++];
			else array[k++] = R[j++];
		}
		while (i < n1) array[k++] = L[i++];
		while (j < n2) array[k++] = R[j++];
	}
}
