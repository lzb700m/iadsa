package s_prj_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import common.Timer;

public class QuickSort {
	private static final Timer timer = new Timer();
	private static final int INSERTION_SORT_CUTOFF = 17;

	/**
	 * public wrapper method for single pivot quick sort
	 * 
	 * @param A
	 *            array to be sorted
	 */
	public static <T extends Comparable<? super T>> void sQuickSort(T[] A) {
		sQuickSort(A, 0, A.length - 1);
	}

	/**
	 * public wrapper method for dual pivot quick sort
	 * 
	 * @param A
	 *            array to be sorted
	 */
	public static <T extends Comparable<? super T>> void dQuickSort(T[] A) {
		dQuickSort(A, 0, A.length - 1);
	}

	/**
	 * Single pivot quick sort
	 * 
	 * @param A
	 *            array to be sorted
	 * @param p
	 *            start index of element in A to be sorted
	 * @param r
	 *            end index of element in A to be sorted
	 */
	private static <T extends Comparable<? super T>> void sQuickSort(T[] A,
			int p, int r) {
		if (r > p) {
			int q = singlePivotPartition(A, p, r);
			sQuickSort(A, p, q - 1);
			sQuickSort(A, q + 1, r);
		}
	}

	/**
	 * Dual pivot quick sort
	 * 
	 * @param A
	 *            array to be sorted
	 * @param p
	 *            start index of element in A to be sorted
	 * @param r
	 *            end index of element in A to be sorted
	 */
	private static <T extends Comparable<? super T>> void dQuickSort(T[] A,
			int p, int r) {
		if (r > p) {
			if (A[p].compareTo(A[r]) > 0) {
				exchange(A, p, r);
			}

			int l = p + 1;
			int g = r - 1;
			int i = p + 1;

			while (i <= g) {
				if (A[i].compareTo(A[p]) < 0) {
					exchange(A, l++, i++);
				} else if (A[i].compareTo(A[r]) > 0) {
					exchange(A, i, g--);
				} else {
					i++;
				}
			}

			exchange(A, p, --l);
			exchange(A, r, ++g);

			dQuickSort(A, p, l - 1);

			if (A[l].compareTo(A[g]) < 0) {
				dQuickSort(A, l + 1, g - 1);
			}

			dQuickSort(A, g + 1, r);
		}
	}

	/**
	 * Partition an array into 2 parts using single pivot, should only be called
	 * if r > p
	 * 
	 * @param A
	 *            array to be partitioned
	 * @param p
	 *            start index of element in A to be partitioned
	 * @param r
	 *            end index of element in A to be partitioned
	 * @return index of pivot in A after partition
	 */
	private static <T extends Comparable<? super T>> int singlePivotPartition(
			T[] A, int p, int r) {
		// precondition : r > p
		int i = median3(A, p, r);
		exchange(A, i, r); // move pivot to end

		T pivot = A[r];
		i = p - 1;

		for (int j = p; j <= r - 1; j++) {
			if (A[j].compareTo(pivot) <= 0) {
				i++;
				exchange(A, i, j);
			}
		}
		exchange(A, ++i, r);
		return i;
	}

	private static int uniformRadom(int min, int max) {
		Random rand = new Random();
		int ret = rand.nextInt((max - min) + 1) + min;
		return ret;
	}

	private static <T extends Comparable<? super T>> int median3(T[] A, int p,
			int r) {
		int mid = (r - p) / 2 + p;
		if (A[p].compareTo(A[mid]) > 0) {
			exchange(A, p, mid);
		}
		if (A[p].compareTo(A[r]) > 0) {
			exchange(A, p, r);
		}
		if (A[mid].compareTo(A[r]) > 0) {
			exchange(A, mid, r);
		}
		return mid;
	}

	private static <T> void exchange(T[] A, int m, int n) {
		T temp = A[m];
		A[m] = A[n];
		A[n] = temp;
	}

	/**
	 * Insertion sort
	 * 
	 * @param A
	 *            array to be partitioned
	 * @param p
	 *            start index of element in A to be sorted
	 * @param r
	 *            end index of element in A to be sorted
	 */
	private static <T extends Comparable<? super T>> void insertionSort(T[] A,
			int p, int r) {
		for (int i = p, j = i; i < r; j = ++i) {
			T ai = A[i + 1];
			while (ai.compareTo(A[j]) < 0) {
				A[j + 1] = A[j];
				if (j-- == p) {
					break;
				}
			}
			A[j + 1] = ai;
		}
	}

	/**
	 * 
	 * @param A
	 * @return
	 */
	private static <T extends Comparable<? super T>> boolean isSorted(T[] A) {
		for (int i = 0; i < A.length - 2; i++) {
			if (A[i].compareTo(A[i + 1]) > 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Driver function
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		/*
		 * input can be read from file (text file containing element of the same
		 * type that is space separated), or from stdin.
		 */
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		List<Integer> list = new ArrayList<Integer>();
		while (in.hasNextInt()) {
			list.add(in.nextInt());
		}
		in.close();

		/*
		 * In this example, we will use integer to test the sorting algorithm.
		 * You can eventually try sorting any object that implements the
		 * Comparable interface
		 */
		Integer[] input = list.toArray(new Integer[list.size()]);
		timer.start();
		sQuickSort(input);
		timer.end();
		System.out.println(isSorted(input));
		System.out.println("Quick sort with single pivot:");
		System.out.println(timer);

		// timer.start();
		// dQuickSort(input);
		// timer.end();
		// System.out.println(isSorted(input));
		// System.out.println("Quick sort with dual pivot:");
		// System.out.println(timer);

		// Integer[] temp = new Integer[input.length];
		// timer.start();
		// s_prj_0.SortComparison.mergeSort(input, temp);
		// timer.end();
		// System.out.println(timer);
	}
}
