package s_prj_4;

/** @author rbk
 *  Binary search tree (nonrecursive version)
 *  Ver 1.1: Bug fixed - parent of child updated after removeOne
 *  
 *  @author Peng Li
 *  Ver 1.1x: short project 4 submission
 *  add level-order traversal;
 *  add constructor for balanced BST using elements of sorted array;
 *  alternate subtree substitute when remove node with 2 children;
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BST<T extends Comparable<? super T>> {
	class Entry<T> {
		T element;
		Entry<T> left, right, parent;

		Entry(T x, Entry<T> l, Entry<T> r, Entry<T> p) {
			element = x;
			left = l;
			right = r;
			parent = p;
		}
	}

	Entry<T> root;
	int size;

	// add boolean indicator for alternating removal of node with 2 children
	boolean removeLeft;

	// default constructor re-wrote using other constructor
	BST() {
		this(null);
	}

	/**
	 * Build a balanced BST using elements of sorted array.
	 * 
	 * @param arr
	 */
	BST(T[] arr) {
		if (arr == null || arr.length == 0) {
			// empty tree
			root = null;
			size = 0;
		} else {
			size = arr.length;
			root = bstHelper(arr, 0, size - 1, null);
		}
	}

	/**
	 * recursive helper method to build a BST from an increasingly sorted array
	 * 
	 * @param arr
	 *            input array
	 * @param start
	 *            start index of arr element to be considered for the sub-tree
	 * @param end
	 *            end index of arr element to be considered for the sub-tree
	 * @param parent
	 *            parent of currently building sub-tree
	 * @return the root node of the sub-tree
	 */
	private Entry<T> bstHelper(T[] arr, int start, int end, Entry<T> parent) {
		if (start > end) {
			// base case 1: reached null
			return null;
		}

		if (start == end) {
			// base case 2: reached leaf node
			return new Entry<T>(arr[start], null, null, parent);
		}

		int mid = start + (end - start) / 2;
		// left and right pointer of root needs to be updated after the
		// recursive call
		Entry<T> root = new Entry<T>(arr[mid], null, null, parent);
		Entry<T> left = bstHelper(arr, start, mid - 1, root);
		Entry<T> right = bstHelper(arr, mid + 1, end, root);
		root.left = left;
		root.right = right;

		return root;
	}

	// Find x in subtree rooted at node t. Returns node where search ends.
	Entry<T> find(Entry<T> t, T x) {
		Entry<T> pre = t;
		while (t != null) {
			pre = t;
			int cmp = x.compareTo(t.element);
			if (cmp == 0) {
				return t;
			} else if (cmp < 0) {
				t = t.left;
			} else {
				t = t.right;
			}
		}
		return pre;
	}

	// Is x contained in tree?
	public boolean contains(T x) {
		Entry<T> node = find(root, x);
		return node == null ? false : x.equals(node.element);
	}

	// Add x to tree. If tree contains a node with same key, replace element by
	// x.
	// Returns true if x is a new element added to tree.
	public boolean add(T x) {
		if (size == 0) {
			root = new Entry<>(x, null, null, null);
		} else {
			Entry<T> node = find(root, x);
			int cmp = x.compareTo(node.element);
			if (cmp == 0) {
				node.element = x;
				return false;
			}
			Entry<T> newNode = new Entry<>(x, null, null, node);
			if (cmp < 0) {
				node.left = newNode;
			} else {
				node.right = newNode;
			}
		}
		size++;
		return true;
	}

	// Remove x from tree. Return x if found, otherwise return null
	public T remove(T x) {
		T rv = null;
		if (size > 0) {
			Entry<T> node = find(root, x);
			if (x.equals(node.element)) {
				rv = node.element;
				remove(node);
				size--;
			}
		}
		return rv;
	}

	// Called when node has at most one child. Returns that child.
	Entry<T> oneChild(Entry<T> node) {
		return node.left == null ? node.right : node.left;
	}

	// Remove a node from tree
	void remove(Entry<T> node) {
		if (node.left != null && node.right != null) {
			removeTwo(node);
		} else {
			removeOne(node);
		}
	}

	// remove node that has at most one child
	void removeOne(Entry<T> node) {
		if (node == root) {
			Entry<T> nc = oneChild(root);
			root = nc;
			root.parent = null;
		} else {
			Entry<T> p = node.parent;
			Entry<T> nc = oneChild(node);
			if (p.left == node) {
				p.left = nc;
			} else {
				p.right = nc;
			}
			if (nc != null)
				nc.parent = p;
		}
	}

	/**
	 * remove a node with 2 children, by alternatively substitute the removed
	 * node with left tree node and right tree node in order to keep the BST
	 * more balanced
	 * 
	 * @param node
	 */
	void removeTwo(Entry<T> node) {
		Entry<T> min = removeLeft ? node.left : node.right;
		if (removeLeft) {
			while (min.right != null) {
				min = min.right;
			}
			removeLeft = false;
		} else {
			while (min.left != null) {
				min = min.left;
			}
			removeLeft = true;
		}
		node.element = min.element;
		removeOne(min);
	}

	public static void main(String[] args) {
		Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		BST<Integer> t = new BST<>(arr);
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				// Comparable[] arr = t.toArray();
				// System.out.print("In-order: ");
				// System.out.println(Arrays.toString(arr));
				break;
			}
		}
		in.close();
		System.out.println("In-order: " + Arrays.toString(arr));
		// test code for level order traversal
		System.out.println("Level-order: "
				+ Arrays.toString(t.levelOrderArray()));
	}

	// Create an array with the elements using in-order traversal of tree
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		inOrder(root, arr, 0);
		return arr;
	}

	// Recursive in-order traversal of tree rooted at "node".
	// "index" is next element of array to be written.
	// Returns index of next entry of arr to be written.
	int inOrder(Entry<T> node, Comparable[] arr, int index) {
		if (node != null) {
			index = inOrder(node.left, arr, index);
			arr[index++] = node.element;
			index = inOrder(node.right, arr, index);
		}
		return index;
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}

	// Preorder traversal of tree
	void preTree(Entry<T> node) {
		if (node != null) {
			System.out.print(" " + node.element);
			preTree(node.left);
			preTree(node.right);
		}
	}

	/**
	 * Level order traversal of the BST
	 * 
	 * @return an array with the elements using a level order traversal of the
	 *         tree
	 */
	public Comparable[] levelOrderArray() {
		Comparable[] arr = new Comparable[size];

		// if root is null, return an empty array
		if (root != null) {
			Queue<Entry<T>> queue = new LinkedList<Entry<T>>();
			queue.offer(root);
			int index = 0;
			while (!queue.isEmpty()) {
				Entry<T> current = queue.poll();
				if (current.left != null) {
					queue.offer(current.left);
				}
				if (current.right != null) {
					queue.offer(current.right);
				}
				arr[index++] = current.element;
			}
		}
		return arr;
	}

}
/*
 * Sample input: 1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
 * 
 * Output: Add 1 : [1] 1 Add 3 : [2] 1 3 Add 5 : [3] 1 3 5 Add 7 : [4] 1 3 5 7
 * Add 9 : [5] 1 3 5 7 9 Add 2 : [6] 1 2 3 5 7 9 Add 4 : [7] 1 2 3 4 5 7 9 Add 6
 * : [8] 1 2 3 4 5 6 7 9 Add 8 : [9] 1 2 3 4 5 6 7 8 9 Add 10 : [10] 1 2 3 4 5 6
 * 7 8 9 10 Remove -3 : [9] 1 2 4 5 6 7 8 9 10 Remove -6 : [8] 1 2 4 5 7 8 9 10
 * Remove -3 : [8] 1 2 4 5 7 8 9 10 Final: 1 2 4 5 7 8 9 10
 * 
 * Extending to AVL tree:
 * 
 * class AVLEntry<T> extends Entry<T> { int height; AVLEntry(T x, Entry<T> l,
 * Entry<T> r, Entry<T> p) { super(x,l,r,p); height = 0; } }
 * 
 * Extending to Red-Black tree:
 * 
 * private static final boolean RED = false; private static final boolean BLACK
 * = true;
 * 
 * class RBEntry<T> extends Entry<T> { boolean color; RBEntry(T x, Entry<T> l,
 * Entry<T> r, Entry<T> p) { super(x,l,r,p); color = RED; } }
 */
