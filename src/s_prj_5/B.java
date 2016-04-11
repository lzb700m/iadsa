package s_prj_5;

import java.util.ArrayList;
import java.util.Arrays;

import common.Timer;

/**
 * b. nPk
 * 
 * Combine the solutions to part a to get Permute(A, n, k): ordered sets of
 * cardinality k from a set of size n.
 * 
 * @author Peng Li
 * @author Nan Zhang
 */
public class B {
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
	public static <T> void npk(T[] data, boolean[] A, int n, int k) {
		if (k > n) {
			return;
		} else if (k == 0) {
			visitCombination(data, A);
		} else {
			npk(data, A, n - 1, k);
			A[n - 1] = true;
			npk(data, A, n - 1, k - 1);
			A[n - 1] = false;
		}
	}

	/**
	 * For each combination, generate all the permutation
	 * 
	 * @param data
	 *            : : T[] - data sequence
	 * @param combination
	 *            : boolean[] - status of each combination generated
	 */
	private static <T> void visitCombination(T[] data, boolean[] combination) {
		ArrayList<T> permData = new ArrayList<T>();
		for (int i = 0; i < combination.length; i++) {
			if (combination[i]) {
				permData.add(data[i]);
			}
		}
		permutationHeaps(permData, permData.size());
		System.out.println();
	}

	/**
	 * Generate all permutations of length n sequence using Heap's algorithm
	 * 
	 * @param data
	 *            : T[] - data sequence
	 * @param n
	 *            : int - length of data sequence
	 */
	private static <T> void permutationHeaps(ArrayList<T> data, int n) {
		if (n == 0) {
			visitPermutation(data);
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
	private static <T> void swap(ArrayList<T> data, int i, int j) {
		T temp = data.get(i);
		data.set(i, data.get(j));
		data.set(j, temp);
	}

	/**
	 * Example visiting function for demonstration, print data
	 * 
	 * @param data
	 *            : T[] - data sequence
	 */
	private static <T> void visitPermutation(ArrayList<T> data) {
		System.out.println(data);
	}

	/**
	 * Driver program for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 4;
		int k = 3;
		boolean[] A = new boolean[n];
		Arrays.fill(A, false);

		Integer[] data = new Integer[n];
		for (int i = 0; i < n; i++) {
			data[i] = i + 1;
		}

		timer.start();
		npk(data, A, n, k);
		timer.end();
		System.out.println("nPk: " + timer + "\n");
	}

}
