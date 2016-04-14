package s_prj_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * d. Enumeration of topological orders in DAGs
 * 
 * Given a DAG as input (use input format used by readGraph), visit and print
 * all topological orders of the graph.
 * 
 * @author Nan Zhang
 * 
 * @author Peng Li
 */
public class D {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length != 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in, true);
		enumTopo(g);
	}

	public static boolean validate(Vertex node) {
		if (node.visiting)
			return false;
		node.visiting = true;
		for (Edge e : node.Adj) {
			if (!e.To.seen) {
				if (!validate(e.To))
					return false;
			}
		}
		node.visiting = false;
		node.seen = true;
		return true;
	}

	public static void enumTopo(Graph g) {
		for (Vertex v : g) {
			if (!v.seen) {
				if (!validate(v))
					return;
			}
		}

		for (Vertex v : g) {
			v.seen = false;
			v.visiting = false;
			for (Edge e : v.Adj) {
				e.To.incoming += 1;
			}

		}
		LinkedHashSet<Vertex> queue = new LinkedHashSet<>();
		for (Vertex v : g) {
			if (v.incoming == 0)
				queue.add(v);
		}
		enumerate(queue, new LinkedList<Integer>());
	}

	public static void enumerate(LinkedHashSet<Vertex> queue,
			LinkedList<Integer> cur) {
		if (queue.size() == 0) {
			System.out.println(Arrays.toString(cur.toArray()));
			return;
		}
		LinkedHashSet<Vertex> new_q = new LinkedHashSet<>(queue);
		for (Vertex v : queue) {
			new_q.remove(v);
			cur.addLast(v.name);
			for (Edge e : v.Adj) {
				e.To.incoming -= 1;
				if (e.To.incoming == 0) {
					new_q.add(e.To);
				}
			}
			enumerate(new_q, cur);
			for (Edge e : v.Adj) {
				e.To.incoming += 1;
				if (e.To.incoming == 1) {
					new_q.remove(e.To);
				}
			}
			cur.removeLast();
			new_q.add(v);
		}
	}
}
