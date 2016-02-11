package s_prj_2;

/**
 * @author Peng Li
 * 
 * Strongly connected components of a directed graph.
 * Implement the algorithm for finding strongly connected components of a
 * directed graph (see page 617 of Cormen et al, Introduction to algorithms, 3rd ed.).
 * Run DFS on G and push the nodes into a stack as they complete DFSVisit().
 * Find G^T, the graph obtained by reversing all edges of G.
 * Run DFS on G^T, but using the stack order in DFS outer loop.
 * Each DSF tree in the second DFS is a strongly connected component.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

import common.Edge;
import common.Graph;
import common.Vertex;

public class SCC {

	public static int stronglyConnectedComponents(Graph g) {

		// initialization
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
		}

		Deque<Vertex> dfsStack = new ArrayDeque<>();

		// first DFS on G
		for (Vertex u : g) {
			if (u.seen == false) {
				dfsVisit(u, dfsStack);
			}
		}

		// re-initialization for second DFS on G transpose
		for (Vertex u : g) {
			u.seen = false;
			u.cno = 0;
		}

		int cno = 0;

		// second DFS is performed in the reverse order of 1st DFS
		while (!dfsStack.isEmpty()) {
			Vertex u = dfsStack.pop();
			if (u.seen == false) {
				dfsVisitTranspose(u, ++cno);
			}
		}

		// output result
		System.out.println("Vertex : cno");
		for (Vertex u : g) {
			System.out.format("%4s   : %2d \n", u, u.cno);
		}

		return cno;
	}

	/*
	 * recursive helper method for finding the sequence of vertex being
	 * completed in DFS
	 */
	private static void dfsVisit(Vertex u, Deque<Vertex> dfsStack) {
		u.seen = true;

		for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if (v.seen == false) {
				v.parent = u;
				dfsVisit(v, dfsStack);
			}
		}

		// vertex is pushed into dfsStack in the order of being finished
		// visiting
		dfsStack.push(u);
	}

	/*
	 * recursive helper method to computer connected components number (cno) on
	 * the transpose graph of g
	 */
	private static void dfsVisitTranspose(Vertex u, int cno) {
		u.seen = true;
		u.cno = cno;

		for (Edge e : u.revAdj) {
			Vertex v = e.otherEnd(u);
			if (v.seen == false) {
				dfsVisitTranspose(v, cno);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;

		if (args.length != 0) {
			File inputFile = new File(args[0]);
			sc = new Scanner(inputFile);
		} else {
			sc = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(sc, true);
		g.printGraph();
		System.out.println("Number of strongly connected components in g: "
				+ stronglyConnectedComponents(g));

	}
}
