package s_prj_5;

import java.util.Arrays;

/**
 * c. Knuth's L algorithm
 * 
 * Implement Knuth's algorithm for generating permutations in lexicographic
 * order.
 * 
 * @author Peng Li
 * @author Nan Zhang
 */
public class C {

	/**
	 * Generate the next permutation of a sequence (with possible duplicate)
	 * 
	 * @param data
	 *            : T[] - original sequence
	 */
	public static <T extends Comparable<T>> T[] nextPermutation(T[] data) {
		int n = data.length;
		int left = -1, right = -1;
		for (int i = n - 1; i > 0; i--) {
			if (data[i].compareTo(data[i - 1]) > 0) {
				left = i - 1;
				break;
			}
		}

		if (left == -1) {
			// if there is no next permutation of input sequence, return the
			// smallest order
			left = 0;
			right = n - 1;
			while (left < right) {
				swap(data, left, right);
				left++;
				right--;
			}
			return data;
		}

		for (int i = n - 1; i > left; i--) {
			if (data[i].compareTo(data[left]) > 0) {
				right = i;
				break;
			}
		}

		swap(data, left, right);
		left = left + 1;
		right = n - 1;
		while (left < right) {
			swap(data, left, right);
			left++;
			right--;
		}
		return data;
	}

	// helper swap function
	private static <T> void swap(T[] data, int i, int j) {
		T temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	/**
	 * Driver program for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 5;
		Integer[] data = new Integer[n];
		for (int i = 0; i < n; i++) {
			data[i] = i + 1;
		}

		for (int i = 0; i < 120; i++) {
			System.out.format("%3d", i + 1);
			System.out.println(": " + Arrays.toString(data));
			data = nextPermutation(data);
		}
	}
}
