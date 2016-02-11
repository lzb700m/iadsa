package s_prj_0;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SortComparison {

	/**
	 * Generic implementation of merge sort (ascending order), besides the input
	 * array that needs to be sorted, an auxiliary array is also passed to the
	 * mergeSort function. It is used during the merge step. The reason is that
	 * Java does not allow create of array of generic type.
	 * 
	 * @param input
	 *            array that needs to be sorted
	 * @param temp
	 *            auxiliary array used in the merge step
	 */
	public static <T extends Comparable<? super T>> void mergeSort(T[] input,
			T[] temp) {
		mergeSort(input, temp, 0, input.length - 1);
	}

	private static <T extends Comparable<? super T>> void mergeSort(T[] input,
			T[] temp, int start, int end) {
		if (start < end) {
			// avoid integer addition overflow
			int mid = start + (end - start) / 2;
			mergeSort(input, temp, start, mid);
			mergeSort(input, temp, mid + 1, end);
			merge(input, temp, start, end, mid + 1);
		}
	}

	private static <T extends Comparable<? super T>> void merge(T[] input,
			T[] temp, int start, int end, int center) {
		int i = start;
		int j = center;
		int k = 0;
		/*
		 * always copy the merged array to temp starting at index 0 of temp
		 */
		while (i < center && j < end + 1) {
			if (input[i].compareTo(input[j]) >= 0) {
				temp[k] = input[j++];
			} else {
				temp[k] = input[i++];
			}
			k++;
		}

		while (i < center) {
			temp[k++] = input[i++];
		}

		while (j < end + 1) {
			temp[k++] = input[j++];
		}

		for (k = 0; k < (end - start + 1); k++) {
			input[start + k] = temp[k];
		}
	}

	/**
	 * a simple implementation of heap sort using Java PriorityQueue interface
	 * 
	 * @param input
	 *            List that needs to be sorted
	 * @return an ArrayList that contains the sorted element in ascending order
	 */
	public static <T extends Comparable<? super T>> List<T> heapSort(
			List<T> input) {
		PriorityQueue<T> pq = new PriorityQueue<T>(input);
		List<T> result = new ArrayList<T>();
		while (!pq.isEmpty()) {
			result.add(pq.remove());
		}
		return result;
	}

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
		Integer[] temp = new Integer[input.length];

		long startTime = System.currentTimeMillis();
		mergeSort(input, temp);
		long endTime = System.currentTimeMillis();
		System.out.println("Mergesort running time: " + (endTime - startTime)
				+ "ms");

		startTime = System.currentTimeMillis();
		heapSort(list);
		endTime = System.currentTimeMillis();
		System.out.println("Heapsort running time: " + (endTime - startTime)
				+ "ms");

		startTime = System.currentTimeMillis();
		Collections.sort(list);
		endTime = System.currentTimeMillis();
		System.out.println("Java Collection.sort() running time: "
				+ (endTime - startTime) + "ms");
	}
}
