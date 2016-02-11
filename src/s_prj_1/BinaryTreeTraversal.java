package s_prj_1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Binary Tree preorder, inorder and postorder traversal implementation using
 * both recursive and iterative method
 * 
 * @author Peng Li
 */
public class BinaryTreeTraversal {
	/*
	 * Definition for a binary tree node.
	 */
	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	/*
	 * Public interface for preorder traversal
	 */
	public static void preorderTraversal(TreeNode root) {
		List<Integer> ret1 = new LinkedList<>();
		List<Integer> ret2 = new LinkedList<>();
		recursivePreorderHelper(ret1, root);
		iterativePreorderHelper(ret2, root);
		System.out.println("Preorder recursive method: " + ret1);
		System.out.println("Preorder iterative method: " + ret2);
	}

	/*
	 * Public interface for inorder traversal
	 */
	public static void inorderTraversal(TreeNode root) {
		List<Integer> ret1 = new LinkedList<>();
		List<Integer> ret2 = new LinkedList<>();
		recursiveInorderHelper(ret1, root);
		iterativeInorderHelper(ret2, root);
		System.out.println("Inorder recursive method: " + ret1);
		System.out.println("Inorder iterative method: " + ret2);
	}

	public static void postorderTraversal(TreeNode root) {
		List<Integer> ret1 = new LinkedList<>();
		List<Integer> ret2 = new LinkedList<>();
		recursivePostorderHelper(ret1, root);
		iterativePostorderHelper(ret2, root);
		System.out.println("Postorder recursive method: " + ret1);
		System.out.println("Postorder iterative method: " + ret2);
	}

	/*
	 * recursive method for binary tree preorder traversal
	 */
	private static void recursivePreorderHelper(List<Integer> list,
			TreeNode node) {
		if (node != null) {
			list.add(node.val);
			recursivePreorderHelper(list, node.left);
			recursivePreorderHelper(list, node.right);
		}
	}

	/*
	 * iterative method for binary tree preorder traversal using stack
	 */
	private static void iterativePreorderHelper(List<Integer> list,
			TreeNode node) {
		if (node == null) {
			return;
		}

		Deque<TreeNode> stack = new ArrayDeque<>();

		while (!stack.isEmpty() || node != null) {
			if (node != null) {
				list.add(node.val);
				if (node.right != null) {
					stack.push(node.right);
				}
				node = node.left;
			} else {
				node = stack.pop();
			}
		}
	}

	/*
	 * recursive method for binary tree inorder traversal
	 */
	private static void recursiveInorderHelper(List<Integer> list, TreeNode node) {
		if (node == null) {
			return;
		}

		recursiveInorderHelper(list, node.left);
		list.add(node.val);
		recursiveInorderHelper(list, node.right);
	}

	/*
	 * iterative method for binary tree inorder traversal using stack
	 */
	private static void iterativeInorderHelper(List<Integer> list, TreeNode node) {
		Deque<TreeNode> stack = new ArrayDeque<>();

		while (!stack.isEmpty() || node != null) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				node = stack.pop();
				list.add(node.val);
				node = node.right;
			}
		}
	}

	/*
	 * recursive method for binary tree postorder traversal
	 */
	private static void recursivePostorderHelper(List<Integer> list,
			TreeNode node) {
		if (node == null) {
			return;
		}

		recursivePostorderHelper(list, node.left);
		recursivePostorderHelper(list, node.right);
		list.add(node.val);
	}

	/*
	 * iterative method for binary tree postorder traversal using stack
	 */
	private static void iterativePostorderHelper(List<Integer> list,
			TreeNode node) {
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode lastVisited = null;

		while (!stack.isEmpty() || node != null) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				TreeNode parentNode = stack.peek();
				if (parentNode.right != null && lastVisited != parentNode.right) {
					// if right child exists AND traversing node from left
					// child, move right
					node = parentNode.right;
				} else {
					list.add(parentNode.val);
					lastVisited = stack.pop();
				}
			}
		}
	}

	public static void main(String[] args) {
		BinaryTreeTraversal test = new BinaryTreeTraversal();
		BinaryTreeTraversal.TreeNode testNode1 = test.new TreeNode(1);
		BinaryTreeTraversal.TreeNode testNode2 = test.new TreeNode(2);
		BinaryTreeTraversal.TreeNode testNode3 = test.new TreeNode(3);
		BinaryTreeTraversal.TreeNode testNode4 = test.new TreeNode(4);
		BinaryTreeTraversal.TreeNode testNode5 = test.new TreeNode(5);
		BinaryTreeTraversal.TreeNode testNode6 = test.new TreeNode(6);
		BinaryTreeTraversal.TreeNode testNode7 = test.new TreeNode(7);
		testNode1.left = testNode2;
		testNode1.right = testNode3;
		testNode2.left = testNode4;
		testNode2.right = testNode5;
		testNode3.left = testNode6;
		testNode3.right = testNode7;
		preorderTraversal(testNode1);
		inorderTraversal(testNode1);
		postorderTraversal(testNode1);

	}
}
