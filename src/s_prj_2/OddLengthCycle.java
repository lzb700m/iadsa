package s_prj_2;

/**
 * @author Peng Li
 * 
 * Finding an odd-length cycle in a non-bipartite graph.
 * Given a graph, find an odd-length cycle and return it.
 * If the graph is bipartite, return null.
 * 
 * Algorithm: run BFS.  If no edge of G connects two nodes at the same
 * level, then the graph is bipartite and has no odd-length cycle.
 * If two nodes u and v at the same level are connected by edge (u,v),
 * then an odd-length cycle can be found by combining the edge (u,v)
 * with the paths from u and v to their least common ancestor in the BFS tree.
 * If g is not connected, this is repeated in each component.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import common.Edge;
import common.Graph;
import common.Vertex;

public class OddLengthCycle {
	/*
	 * a graph is a bipartite iff it has no odd length cycle
	 */
	public static List<Vertex> oddLengthCycle(Graph g) {

		// initialization for BFS
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			u.distance = Integer.MAX_VALUE;
		}

		Queue<Vertex> queue = new LinkedList<>();
		/*
		 * indicator if such odd length cycle has been found, this program finds
		 * "a" odd length cycle if exists, not all of them
		 */
		boolean found = false;
		// 2 nodes at the same level that are connected by an edge
		Vertex x = null;
		Vertex y = null;

		for (Vertex start : g) {
			// for every connected component
			if (!start.seen) {
				start.distance = 0;
				queue.offer(start);
				start.seen = true;

				while (!found && !queue.isEmpty()) {
					Vertex u = queue.poll();

					for (Edge e : u.Adj) {
						Vertex v = e.otherEnd(u);

						if (!v.seen) {
							v.parent = u;
							v.distance = u.distance + 1;
							v.seen = true;
							queue.offer(v);
						} else {
							// odd length cycle found, graph not bipartite
							if (u.distance == v.distance) {
								// this will stop the BFS of current connected
								// component
								found = true;
								x = u;
								y = v;
								break;
							}
						}
					}
				}
			}

			if (found) {
				// this will stop exploring other connected components
				break;
			}
		}

		// find the odd length cycle path and add them to result
		if (x != null && y != null) {
			// odd length cycle exists
			List<Vertex> ret = new LinkedList<>(); // result
			/*
			 * use a queue and a stack to store the vertex on the path as we
			 * find the common ancestor of them for easier path extraction
			 */
			Queue<Vertex> halfRoute1 = new LinkedList<>();
			Deque<Vertex> halfRoute2 = new ArrayDeque<>();
			halfRoute2.push(x);
			while (x != y) {
				halfRoute1.offer(x);
				halfRoute2.push(y);
				x = x.parent;
				y = y.parent;
			}
			halfRoute1.offer(x);
			while (!halfRoute1.isEmpty()) {
				ret.add(halfRoute1.poll());
			}
			while (!halfRoute2.isEmpty()) {
				ret.add(halfRoute2.pop());
			}
			return ret;

		} else {
			// input graph is bipartite
			return null;
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
		List<Vertex> oddLengthCycle = oddLengthCycle(g);

		System.out.println("The odd length cycle is: " + oddLengthCycle
				+ "; length is: " + (oddLengthCycle.size() - 1) + ".");
	}
}
