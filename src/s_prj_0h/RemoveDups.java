package s_prj_0h;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Given an array of unsorted objects of some class (that implements hashCode
 * and equals), move the distinct elements of the array to the front. The
 * function behaves as follows. Let k be the number of distinct elements of A (k
 * is not known). Find the k distinct elements of arr[], and move them to
 * arr[0..k-1]. Return k. Use hashing to implement the algorithm in expected
 * O(n) time. Signature: public static int findDistinct(T[] arr)
 * 
 * @author Peng Li
 *
 */
public class RemoveDups {
	public static <T> int findDistinct(T[] arr) {
		// uniques: set of unique object
		// dupPos: queue of index of duplicate object in arr

		Set<T> uniques = new HashSet<>();
		Queue<Integer> dupPos = new LinkedList<>();

		// if arr[i] does not have duplicate object so far, put object arr[i] in
		// uniques set, and exchange it with the first available position that
		// contains a duplicate object, no exchange necessary if no duplicates
		// found so far.
		//
		// if arr[i] has duplicate, put the index if arr[i] into the queue
		// dupPos
		for (int i = 0; i < arr.length; i++) {
			if (!uniques.contains(arr[i])) {
				uniques.add(arr[i]);
				if (!dupPos.isEmpty()) {
					int j = dupPos.remove();
					exchange(arr, i, j);
					dupPos.add(i);
				}
			} else {
				dupPos.add(i);
			}
		}

		return uniques.size();
	}

	private static <T> void exchange(T[] arr, int i, int j) {
		T temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		Integer[] test = { 1, 1, 2, 2, 3, 3, 4, 5 };
		System.out.println(findDistinct(test));
		System.out.println(Arrays.toString(test));
	}
}
