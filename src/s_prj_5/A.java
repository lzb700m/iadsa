package s_prj_5;

import java.util.Arrays;

import common.Timer;

/**
 * a. Permutations and combinations
 * 
 * Implement the following algorithms discussed in class: (a) Combination(A, n,
 * k), (b) Permute(A, n). For Permute(), implement Take 2 and Heap's algorithms,
 * and compare their running times for n = 8..14.
 * 
 * @author Peng Li
 * @author Nan Zhang
 */
public class A {
	private static final Timer timer = new Timer();

	/**
	 * Generate all length k combinations of length n sequence
	 * 
	 * @param A
	 *            : boolean[] - status of each combination generated
	 * @param n
	 *            : length of original sequence
	 * @param k
	 *            : length of combination
	 */
	public static <T> void combination(T[] data, boolean[] A, int n, int k) {

		if (k > n) {
			return;
		} else if (k == 0) {
			visitCombination(data, A);
		} else {
			combination(data, A, n - 1, k);
			A[n - 1] = true;
			combination(data, A, n - 1, k - 1);
			A[n - 1] = false;
		}
	}

	/**
	 * Example visiting function for demonstration, print data if in
	 * combination, "--" otherwise
	 * 
	 * @param data
	 *            : T[] - data sequence
	 * @param combination
	 *            : boolean[] - status of each combination generated
	 */
	private static <T> void visitCombination(T[] data, boolean[] combination) {
		for (int i = 0; i < combination.length; i++) {
			if (combination[i]) {
				System.out.format("%2d", data[i]);
			} else {
				System.out.print("--");
			}
		}
		System.out.println();
	}

	/**
	 * Generate all permutations of length n sequence using Take 2
	 * 
	 * @param data
	 *            : T[] - data sequence
	 * @param n
	 *            : int - length of data sequence
	 */
	public static <T> void permutation(T[] data, int n) {
		if (n == 0) {
			// visitPermutation(data);
		} else {
			for (int i = 0; i < n; i++) {
				swap(data, i, n - 1);
				permutation(data, n - 1);
				swap(data, n - 1, i);
			}
		}
	}

	/**
	 * Generate all permutations of length n sequence using Heap's algorithm
	 * 
	 * @param data
	 *            : T[] - data sequence
	 * @param n
	 *            : int - length of data sequence
	 */
	public static <T> void permutationHeaps(T[] data, int n) {
		if (n == 0) {
			// visitPermutation(data);
		} else {
			for (int i = 0; i < n; i++) {
				permutationHeaps(data, n - 1);
				if (n % 2 == 0) {
					swap(data, i, n - 1);
				} else {
					swap(data, 0, n - 1);
				}
			}
		}
	}

	// helper swap function
	private static <T> void swap(T[] data, int i, int j) {
		T temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	/**
	 * Example visiting function for demonstration, print data
	 * 
	 * @param data
	 *            : T[] - data sequence
	 */
	private static <T> void visitPermutation(T[] data) {
		System.out.println(Arrays.toString(data));
	}

	/**
	 * Driver program for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 10;
		int k = 3;
		boolean[] A = new boolean[n];
		Arrays.fill(A, false);

		Integer[] data = new Integer[n];
		for (int i = 0; i < n; i++) {
			data[i] = i + 1;
		}

		timer.start();
		combination(data, A, n, k);
		timer.end();
		System.out.println("Combination: " + timer + "\n");

		timer.start();
		permutation(data, n);
		timer.end();
		System.out.println("Permutataion: " + timer + "\n");

		timer.start();
		permutationHeaps(data, n);
		timer.end();
		System.out.println("Heap's Permutataion: " + timer + "\n");

	}
}
