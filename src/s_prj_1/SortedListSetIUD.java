package s_prj_1;

/**
 * Implement methods for generating the intersection, union and difference of
 * given 2 sorted list (ArrayList or LinkedList) implementing set.
 * 
 * @author Peng Li
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SortedListSetIUD {
	public static <T extends Comparable<? super T>> void intersect(List<T> l1,
			List<T> l2, List<T> outList) {

		Iterator<T> itL1 = l1.iterator();
		Iterator<T> itL2 = l2.iterator();

		T itemL1 = nextHelper(itL1);
		T itemL2 = nextHelper(itL2);

		while (itemL1 != null && itemL2 != null) {

			if (itemL1.compareTo(itemL2) < 0) {
				itemL1 = nextHelper(itL1);
			} else if (itemL1.compareTo(itemL2) > 0) {
				itemL2 = nextHelper(itL2);
			} else {
				outList.add(itemL1);
				itemL1 = nextHelper(itL1);
				itemL2 = nextHelper(itL2);
			}
		}
	}

	public static <T extends Comparable<? super T>> void union(List<T> l1,
			List<T> l2, List<T> outList) {

		Iterator<T> itL1 = l1.iterator();
		Iterator<T> itL2 = l2.iterator();

		T itemL1 = nextHelper(itL1);
		T itemL2 = nextHelper(itL2);

		while (itemL1 != null && itemL2 != null) {

			if (itemL1.compareTo(itemL2) < 0) {
				outList.add(itemL1);
				itemL1 = nextHelper(itL1);
			} else if (itemL1.compareTo(itemL2) > 0) {
				outList.add(itemL2);
				itemL2 = nextHelper(itL2);
			} else {
				outList.add(itemL1);
				itemL1 = nextHelper(itL1);
				itemL2 = nextHelper(itL2);
			}
		}

		while (itemL1 != null) {
			outList.add(itemL1);
			itemL1 = nextHelper(itL1);
		}

		while (itemL2 != null) {
			outList.add(itemL2);
			itemL2 = nextHelper(itL2);
		}
	}

	public static <T extends Comparable<? super T>> void difference(List<T> l1,
			List<T> l2, List<T> outList) {

		Iterator<T> itL1 = l1.iterator();
		Iterator<T> itL2 = l2.iterator();

		T itemL1 = nextHelper(itL1);
		T itemL2 = nextHelper(itL2);

		while (itemL1 != null && itemL2 != null) {

			if (itemL1.compareTo(itemL2) < 0) {
				outList.add(itemL1);
				itemL1 = nextHelper(itL1);
			} else if (itemL1.compareTo(itemL2) > 0) {
				itemL2 = nextHelper(itL2);
			} else {
				itemL1 = nextHelper(itL1);
				itemL2 = nextHelper(itL2);
			}
		}

		while (itemL1 != null) {
			outList.add(itemL1);
			itemL1 = nextHelper(itL1);
		}
	}

	private static <T> T nextHelper(Iterator<T> it) {
		return (it.hasNext()) ? it.next() : null;
	}

	public static void main(String[] args) {
		Integer[] arr1 = { 2, 3, 4, 5, 7, 8, 9, 10, 1000 };
		Integer[] arr2 = { 2, 3, 5, 6, 8 };

		List<Integer> arrayList1 = new ArrayList<>(Arrays.asList(arr1));
		List<Integer> arrayList2 = new ArrayList<>(Arrays.asList(arr2));
		List<Integer> arrayListOut = new ArrayList<>();

		List<Integer> linkedList1 = new LinkedList<>(Arrays.asList(arr1));
		List<Integer> linkedList2 = new LinkedList<>(Arrays.asList(arr2));
		List<Integer> linkedListOut = new LinkedList<>();

		System.out.println("ArrayList test:");
		System.out.println("List1 = " + arrayList1);
		System.out.println("List2 = " + arrayList2);
		intersect(arrayList1, arrayList2, arrayListOut);
		System.out.println("Intersection: " + arrayListOut);
		arrayListOut.clear();
		union(arrayList1, arrayList2, arrayListOut);
		System.out.println("Union: " + arrayListOut);
		arrayListOut.clear();
		difference(arrayList1, arrayList2, arrayListOut);
		System.out.println("Difference(List1 - List2): " + arrayListOut + "\n");

		System.out.println("Linkedlist test:");
		System.out.println("List1 = " + linkedList1);
		System.out.println("List2 = " + linkedList2);
		intersect(linkedList1, linkedList2, linkedListOut);
		System.out.println("Intersection: " + linkedListOut);
		linkedListOut.clear();
		union(linkedList1, linkedList2, linkedListOut);
		System.out.println("Union: " + linkedListOut);
		linkedListOut.clear();
		difference(linkedList1, linkedList2, linkedListOut);
		System.out.println("Difference(List1 - List2): " + linkedListOut);

	}
}
