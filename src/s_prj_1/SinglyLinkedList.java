package s_prj_1;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author rbk Singly linked list: for instructional purposes only
 * 
 * @author Peng Li implement multiUnzip(), reverseIterative(),
 *         reverseRecursive(), reversePrintIterative(), reversePrintRecursive()
 *         functions
 */

public class SinglyLinkedList<T> {
	public class Entry<T> {
		T element;
		Entry<T> next;

		Entry(T x, Entry<T> nxt) {
			element = x;
			next = nxt;
		}
	}

	Entry<T> header, tail;
	int size;

	SinglyLinkedList() {
		header = new Entry<>(null, null);
		tail = null;
		size = 0;
	}

	void add(T x) {
		if (tail == null) {
			header.next = new Entry<>(x, header.next);
			tail = header.next;
		} else {
			tail.next = new Entry<>(x, null);
			tail = tail.next;
		}
		size++;
	}

	void printList() {
		Entry<T> x = header.next;
		while (x != null) {
			System.out.print(x.element + " ");
			x = x.next;
		}
		System.out.println();
	}

	void unzip() {
		if (size < 3) { // Too few elements. No change.
			return;
		}

		Entry<T> tail0 = header.next;
		Entry<T> head1 = tail0.next;
		Entry<T> tail1 = head1;
		Entry<T> c = tail1.next;
		int state = 0;

		// Invariant: tail0 is the tail of the chain of elements with even
		// index.
		// tail1 is the tail of odd index chain.
		// c is current element to be processed.
		// state indicates the state of the finite state machine
		// state = i indicates that the current element is added after taili
		// (i=0,1).
		while (c != null) {
			if (state == 0) {
				tail0.next = c;
				tail0 = c;
				c = c.next;
			} else {
				tail1.next = c;
				tail1 = c;
				c = c.next;
			}
			state = 1 - state;
		}
		tail0.next = head1;
		tail1.next = null;
	}

	/*
	 * Loop-invariant: @tails[i] references the end of the ith sublist broken by
	 * unzip operation; @heads[i] keeps the start of the ith sublist broken by
	 * unzip operation; @state represent the finite state machine state (0 - k).
	 */
	void multiUnzip(int k) {
		if (size < k + 1) {
			return; // too few elements, no change
		}

		List<Entry<T>> tails = new ArrayList<>();
		List<Entry<T>> heads = new ArrayList<>();
		Entry<T> c = header.next;

		// initialize tails and head array
		for (int i = 0; i < k; i++) {
			tails.add(c);
			heads.add(c);
			c = c.next;
		}
		int state = 0;

		// break the list into k pieces
		while (c != null) {
			tails.get(state).next = c;
			tails.set(state, c);
			c = c.next;
			state = (state + 1) % k;
		}

		// combine the k pieces
		for (int i = 0; i < k - 1; i++) {
			tails.get(i).next = heads.get(i + 1);
		}
		tails.get(k - 1).next = null;
	}

	/*
	 * non-recursive method to reverse the SinglyLinkedList
	 */
	void reverseIterative() {
		Entry<T> prev = null;
		Entry<T> current = this.header.next;
		Entry<T> temp;

		// Loop-invariant: all nodes before current node is properly reversed
		// except the header to the first node reference
		while (current != null) {
			temp = current.next;
			current.next = prev;
			prev = current;
			current = temp;
		}

		// maintain internal structure of SinglyLinkedList
		this.tail = this.header.next;
		this.header.next = prev;
	}

	/*
	 * recursive method to reverse the SinglyLinkedList
	 */
	void reverseRecursive() {
		Entry<T> prev = null;
		Entry<T> current = this.header.next;
		reverseHelper(prev, current);
		this.tail = current;
	}

	private void reverseHelper(Entry<T> prev, Entry<T> current) {
		if (current == null) {
			this.header.next = prev;
		} else {
			reverseHelper(current, current.next);
			current.next = prev;
		}
	}

	/*
	 * non-recursive method to print the SinglyLinkedList in reverse order
	 */
	void reversePrintIterative() {
		Deque<T> stack = new ArrayDeque<>();
		Entry<T> current = header.next;

		// Loop-invariant: all nodes before current got pushed into stack
		while (current != null) {
			stack.push(current.element);
			current = current.next;
		}

		// Loop-invariant: nodes got popped in FILO order
		while (!stack.isEmpty()) {
			System.out.print(stack.pop() + " ");
		}
		System.out.println();
	}

	/*
	 * recursive method to print the SinglyLinkedList in reverse order
	 */
	void reversePrintRecursive() {
		reversePrintHelper(header.next);
		System.out.println();
	}

	private void reversePrintHelper(Entry<T> current) {
		if (current != null) {
			reversePrintHelper(current.next);
			System.out.print(current.element + " ");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(new Integer(i));
		}

		System.out.print("Original list: ");
		lst.printList();
		System.out.println();

		System.out.print("Reversed list (using recursive method): ");
		lst.reverseRecursive();
		lst.printList();
		System.out.print("Reversed back list (using iterative method): ");
		lst.reverseIterative();
		lst.printList();
		System.out.println();

		System.out.print("Reversed order printing (using recursive method): ");
		lst.reversePrintRecursive();
		System.out.print("Reversed order printing (using iterative method): ");
		lst.reversePrintIterative();
		System.out.println();

		lst.multiUnzip(3);
		System.out.print("After unzip 3: ");
		lst.printList();
	}
}
