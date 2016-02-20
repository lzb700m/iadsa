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
	 * public wrapper method for quick sort
	 * 
	 * @param A
	 *            array to be sorted
	 */
	public static <T extends Comparable<? super T>> void quickSort(T[] A) {
		quickSort(A, 0, A.length - 1);
	}

	/**
	 * Traditional quick-sort with one pivot implementation
	 * 
	 * @param A
	 *            array to be sorted
	 * @param p
	 *            start index of element in A to be sorted
	 * @param r
	 *            end index of element in A to be sorted
	 */
	private static <T extends Comparable<? super T>> void quickSort(T[] A,
			int p, int r) {
		if (r - p >= INSERTION_SORT_CUTOFF) {
			int q = partitionOne(A, p, r);
			quickSort(A, p, q - 1);
			quickSort(A, q + 1, r);
		} else {
			insertionSort(A, p, r);
		}
	}

	/**
	 * Partition an array into 2 parts given one pivot
	 * 
	 * @param A
	 *            array to be partitioned
	 * @param p
	 *            start index of element in A to be partitioned
	 * @param r
	 *            end index of element in A to be partitioned
	 * @return index of pivot in A after partition
	 */
	private static <T extends Comparable<? super T>> int partitionOne(T[] A,
			int p, int r) {
		// int i = uniformRadom(p, r);
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

	/**
	 * Generate a uniform random number from a given interval
	 * 
	 * @param min
	 *            minimum value of the random number
	 * @param max
	 *            maximum value of the random number
	 * @return
	 */
	private static int uniformRadom(int min, int max) {
		Random rand = new Random();
		int ret = rand.nextInt((max - min) + 1) + min;
		return ret;
	}

	/**
	 * 
	 * @param A
	 * @param p
	 * @param r
	 * @return
	 */
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

	/**
	 * Exchange 2 element in an array
	 * 
	 * @param A
	 *            input array contains elements to be exchanged
	 * @param m
	 *            index of first element
	 * @param n
	 *            index of second element
	 */
	private static <T> void exchange(T[] A, int m, int n) {
		T temp = A[m];
		A[m] = A[n];
		A[n] = temp;
	}

	/**
	 * Insertion sort
	 * 
	 * @param A
	 * @param p
	 * @param r
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
		quickSort(input);
		timer.end();
		System.out.println(timer);

		// Integer[] temp = new Integer[input.length];
		// timer.start();
		// s_prj_0.SortComparison.mergeSort(input, temp);
		// timer.end();
		// System.out.println(timer);
	}
}
