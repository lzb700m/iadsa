package s_prj_2;

/**
 * @author Peng Li
 * 
 * A graph G is called Eulerian if it is connected and the degree of
 * every vertex is an even number.  It is known that such graphs have a
 * cycle (not simple) that goes through every edge of the graph
 * exactly once.  A connected graph that has exactly 2 vertices of odd
 * degree has an Eulerian path.  Write a function that outputs one of
 * the messages that applies to the given graph.
 * 
 * Possible outputs:
 *  Graph is Eulerian.
 *  Graph has an Eulerian Path between vertices ?? and ??.
 *  Graph is not connected.
 *  Graph is not Eulerian.  It has ?? vertices of odd degree.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import common.Edge;
import common.Graph;
import common.Vertex;

public class Eulerian {
	public static void testEulerian(Graph g) {
		/*
		 * step 1: use DFS to find out if g is connected or not; step 2: if
		 * connected, find number of nodes with odd degree; step 3: output
		 * result
		 */

		for (Vertex u : g) {
			u.seen = false;
			u.cno = 0;
		}

		int cno = 0;
		List<Vertex> oddEdgeVertexs = new LinkedList<>();

		for (Vertex u : g) {
			if (u.seen == false) {
				dfsVisit(u, ++cno, oddEdgeVertexs);
			}
		}

		if (cno > 1) {
			System.out.println("Graph is not connected.");
		} else {
			int count = oddEdgeVertexs.size();
			if (count == 0) {
				System.out.println("Graph is Eulerian.");
			} else if (count == 2) {
				Vertex start = oddEdgeVertexs.get(0);
				Vertex end = oddEdgeVertexs.get(1);
				System.out
						.println("Graph has an Eulerian Path between vertices "
								+ start + " and " + end + ".");
			} else {
				System.out.println("Graph is not Eulerian.  It has " + count
						+ " vertices of odd degree.");
			}
		}
	}

	/*
	 * recursive DFS helper method for 2 purposes: 1. calculate connected
	 * components 2. find vertices with odd number of edges
	 */
	private static void dfsVisit(Vertex u, int cno, List<Vertex> oddEdgeList) {
		u.seen = true;
		u.cno = cno;
		if (u.Adj.size() % 2 != 0) {
			oddEdgeList.add(u);
		}

		for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if (v.seen == false) {
				dfsVisit(v, cno, oddEdgeList);
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

		Graph g = Graph.readGraph(sc, false);
		testEulerian(g);
	}
}
