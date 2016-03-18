package s_prj_0h;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import common.Timer;

/**
 * Write a function that takes as parameter an array of integers, and returns an
 * integer that is most frequent in the array. Signature: public static int
 * mostFrequent(int[] arr). Compare the performance of an O(nlogn) algorithm
 * that sorts the array with Arrays.sort and then finds the element, with a
 * solution using HashMap that runs in O(n) expected time.
 * 
 * @author Peng Li
 *
 */
public class MostFrequentElement {
	public static int mostFrequent(int[] arr) {

		int maxCount = 1;
		int ret = arr[0];
		Map<Integer, Integer> counts = new HashMap<>();

		for (Integer i : arr) {
			if (counts.containsKey(i)) {
				counts.put(i, counts.get(i) + 1);
			} else {
				counts.put(i, 1);
			}
		}

		for (Entry<Integer, Integer> entry : counts.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				ret = entry.getKey();
			}
		}
		System.out.println("Most Frequent: " + ret + ", count: " + maxCount);

		return ret;
	}

	public static int mostFrequentSorting(int[] arr) {
		Arrays.sort(arr);
		int maxCount = 1; // maximum count of identical element
		int count = 1; // current count of identical element
		int ret = arr[0];

		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] == arr[i + 1]) {
				count++;
			} else {
				if (count > maxCount) {
					maxCount = count;
					ret = arr[i];
				}
				count = 1;
			}
		}
		System.out.println("Most Frequent: " + ret + ", count: " + maxCount);

		return ret;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Timer timer = new Timer();
		Scanner sc;
		if (args.length > 0) {
			sc = new Scanner(new File(args[0]));
		} else {
			sc = new Scanner(System.in);
		}

		List<Integer> list = new ArrayList<Integer>();
		while (sc.hasNextInt()) {
			list.add(sc.nextInt());
		}
		sc.close();

		int[] input = new int[list.size()];
		for (int i = 0; i < input.length; i++) {
			input[i] = list.get(i);
		}

		timer.start();
		System.out.println(mostFrequent(input));
		timer.end();
		System.out.println(timer);

		timer.start();
		System.out.println(mostFrequentSorting(input));
		timer.end();
		System.out.println(timer);
	}
}
