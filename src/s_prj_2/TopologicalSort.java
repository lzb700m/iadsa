package s_prj_2;

/**
 * @author Peng Li
 * 
 * Implement two algorithms for ordering the nodes of a DAG topologically. 
 * Both algorithms should return null if the given graph is not a DAG.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import common.Edge;
import common.Graph;
import common.Vertex;

public class TopologicalSort {
	/**
	 * Algorithm 1. Remove vertices with no incoming edges, one at a time, along
	 * with their incident edges, and add them to a list.
	 * 
	 */
	public static List<Vertex> toplogicalOrder1(Graph g) {
		/*
		 * initialization: calculate in-degree of all vertex, add all vertex
		 * with zero in-degree into queue
		 */
		/*
		 * @remainingDegree is used to simulate edge removal, because we do not
		 * want to modify the input graph
		 */
		int[] remainingDegree = new int[g.verts.size()];
		Queue<Vertex> zeroDegreeVertexs = new LinkedList<>();
		for (Vertex u : g) {
			int inDegree = u.revAdj.size();
			/*
			 * here we assume that the input graph vertex name are sequential
			 * and starts from 1
			 */
			remainingDegree[u.name] = inDegree;
			if (inDegree == 0) {
				zeroDegreeVertexs.offer(u);
			}
		}

		List<Vertex> ret = new ArrayList<>(); // result list to be returned

		while (!zeroDegreeVertexs.isEmpty()) {
			Vertex u = zeroDegreeVertexs.poll();
			ret.add(u);
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				remainingDegree[v.name]--; // simulation of edge removal
				if (remainingDegree[v.name] == 0) {
					zeroDegreeVertexs.offer(v);
				}
			}
		}

		for (int i = 1; i < remainingDegree.length; i++) {
			if (remainingDegree[i] > 0) {
				// still edge remaining after topological sort, cycle detected
				return null;
			}
		}

		return ret;
	}

	/**
	 * Algorithm 2. Run DFS on g and push nodes to a stack in the order in which
	 * they finish. Write code without using global variables.
	 * 
	 * Implemented using a recursive helper method by passing the result list
	 * and visiting vertex as parameter
	 */
	public static Stack<Vertex> toplogicalOrder2(Graph g) {

		// initialization
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
		}

		Stack<Vertex> ret = new Stack<>(); // result
		/*
		 * parents nodes of currently being visited node, simulation of OS stack
		 */
		Stack<Vertex> parents = new Stack<>();

		for (Vertex u : g) {
			// for every weakly connected component
			if (u.seen == false) {
				try {
					dfsVisit(u, ret, parents);
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
					return null;
				}
			}
		}
		return ret;
	}

	private static void dfsVisit(Vertex u, Stack<Vertex> topoSort,
			Stack<Vertex> parents) {
		u.seen = true;
		parents.push(u);

		for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if (parents.contains(v)) {
				// back edge found, cycle exists
				throw new IllegalArgumentException(
						"Cycle found in input graph.");
			}
			if (v.seen == false) {
				v.parent = u;
				dfsVisit(v, topoSort, parents);
			}
		}
		topoSort.push(u);
		parents.pop();
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
		System.out.println(toplogicalOrder1(g));
		System.out.println(toplogicalOrder2(g));
	}
}
