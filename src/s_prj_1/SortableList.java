package s_prj_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Write the Merge sort algorithm that works on linked lists. This will be a
 * member function of a linked list class, so that it can work with the internal
 * details of the class. The function should use only O(log n) extra space
 * (mainly for recursion), and not make copies of elements unnecessarily. You
 * can start from the SinglyLinkedList class provided or create your own.
 * 
 * @author Peng Li
 */

public class SortableList<T extends Comparable<? super T>> extends
		SinglyLinkedList<T> {
	/*
	 * merge 2 sorted linked list (merge input list into "this")
	 */
	public void merge(SortableList<T> l2) {
		Entry<T> p1Prev = this.header;
		Entry<T> p1 = this.header.next;
		Entry<T> p2 = l2.header.next;

		while (p1 != null && p2 != null) {
			if (p1.element.compareTo(p2.element) <= 0) {
				p1Prev = p1Prev.next;
				p1 = p1.next;
			} else {
				p1Prev.next = p2;
				p2 = p2.next;
				p1Prev.next.next = p1;
				p1Prev = p1Prev.next;
				this.size++;
			}
		}

		// TODO this can be improved
		/*
		 * Professor comment: do not use "this.add(p2.element);". Instead, link
		 * remaining list to p1Prev in O(1) time.
		 */
		while (p2 != null) {
			this.add(p2.element);
			p2 = p2.next;
		}
	}

	/*
	 * recursive function for mergesort
	 */
	void mergeSort() {
		if (this.size > 1) {
			// break the list into 2 halves
			SortableList<T> secondHalf = this.breakList();

			this.mergeSort();
			secondHalf.mergeSort();

			// merge 2 sorted list
			this.merge(secondHalf);
		}
	}

	/*
	 * internal method for breaking "this" into 2 halves, "this" will become the
	 * first half of the split, second half of the list is return by this method
	 */
	private SortableList<T> breakList() {
		Entry<T> newHeader = new Entry<T>(null, this.header);

		/*
		 * find the mid point of "this"
		 */
		for (int i = 0; i < this.size / 2; i++) {
			newHeader.next = newHeader.next.next;
		}

		SortableList<T> newList = new SortableList<T>();

		/*
		 * maintain internal structure (header, tail, size) of "this" and new
		 * list
		 */
		newList.header = new Entry<T>(null, newHeader.next.next);
		newList.tail = this.tail;
		newList.size = this.size - this.size / 2;

		this.tail = newHeader.next;
		this.tail.next = null; // break the list into 2 halves
		this.size = this.size / 2;

		return newList;
	}

	/*
	 * public function (driver) for mergesort
	 */
	public static <T extends Comparable<? super T>> void mergeSort(
			SortableList<T> lst) {
		lst.mergeSort();
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		SortableList<Integer> list = new SortableList<>();
		while (in.hasNextInt()) {
			list.add(in.nextInt());
		}
		in.close();

		System.out.print("Input list: ");
		list.printList();
		System.out.print("Sorted list: ");
		list.mergeSort();
		list.printList();
	}
}
