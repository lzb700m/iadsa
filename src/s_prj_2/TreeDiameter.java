package s_prj_2;

/**
 * @author Peng Li
 * 
 * In this problem, you are given an unrooted tree as input.  Since
 * the tree may not be a binary tree, we will represent it with an
 * adjacency list (i.e., it is a graph that happens to be a tree).
 * The following algorithm can be used to find its diameter:
 *
 * 1. Run BFS from an arbitrary node as root.
 * 2. Select a node Z with maximum distance from the first BFS.
 * 3. Run a second BFS with Z as root.
 * 4. The diameter of the tree is the maximum distance to any node from Z in BFS 2.
 *
 * If the input graph is not a tree, return -1.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import common.Edge;
import common.Graph;
import common.Vertex;

public class TreeDiameter {
	public static int diameter(Graph t) {

		// initialize nodes
		for (Vertex u : t) {
			u.seen = false;
			u.parent = null;
			u.distance = Integer.MAX_VALUE;
		}

		// find any node as start node
		Vertex start = t.verts.get(1); // verts index starts at 1
		start.distance = 0;
		try {
			/*
			 * first BFS, find the farthest vertex from the randomly selected
			 * start vertex
			 */
			bfsDistance(start);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return -1;
		}

		for (Vertex u : t) {
			if (!u.seen) {
				System.out.println("Input graph is not a tree, not connected");
				return -1; // not connected
			}
			/*
			 * select node with maximum distance from first BFS
			 */
			start = (start.distance < u.distance) ? u : start;
		}

		// re-initialization for second BFS
		for (Vertex u : t) {
			u.seen = false;
			u.parent = null;
			u.distance = 0;
		}

		try {
			bfsDistance(start);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return -1;
		}

		int ret = Integer.MIN_VALUE;
		for (Vertex u : t) {
			// find the maximum distance from the second BFS
			ret = u.distance > ret ? u.distance : ret;
		}

		return ret;
	}

	/**
	 * helper function to perform BFS to update distance from start vertex,
	 * throw IllegalArgumentException if input graph has cycle
	 * 
	 * @param start
	 *            BFS start vertex
	 */
	private static void bfsDistance(Vertex start) {
		Queue<Vertex> queue = new LinkedList<>();
		queue.offer(start);
		int countPerLevel = 1; // one node for the first level of BFS
		int distance = 1;

		while (!queue.isEmpty()) {
			Vertex u = queue.poll();
			u.seen = true;
			countPerLevel--;

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (v != u.parent) {
					if (v.seen == true) {
						throw new IllegalArgumentException(
								"Input graph is not a tree, cycle found");
					}
					v.parent = u;
					v.distance = distance;
					queue.offer(v);
				}
			}

			// increment distance if current level has been traversed
			if (countPerLevel == 0) {
				countPerLevel = queue.size();
				distance++;
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		if (args.length != 0) {
			File file = new File(args[0]);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(sc, false);
		System.out.println("The diameter of the tree is: " + diameter(g));
	}
}
