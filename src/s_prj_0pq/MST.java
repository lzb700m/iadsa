package s_prj_0pq;

/**
 * @author Peng Li
 * 
 * Implementation Prim algorithm of minimum spanning tree
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import common.Edge;
import common.Graph;
import common.Vertex;

public class MST {
	static final int Infinity = Integer.MAX_VALUE;

	/**
	 * Implementation of Prim algorithm using priority queue of Edges. Running
	 * time: O(|E|*log(|E|))
	 * 
	 * @param g
	 *            input graph (assume connected)
	 * @return weight sum of minimum spanning tree
	 */
	public static int PrimMST1(Graph g) {
		// initialization
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
		}

		int wmst = 0; // weight sum of MST
		Vertex src = g.verts.get(1); // verts[0] is null, pick any vertex
		Edge comp = new Edge(); // use as comparator argument
		PQ<Edge> pq = new BinaryHeap<Edge>(0, comp);

		src.seen = true;
		for (Edge e : src.Adj) {
			pq.insert(e);
		}

		while (!pq.isEmpty()) {
			Edge e = pq.deleteMin();

			if (e.From.seen && e.To.seen) {
				continue; // add e will create a cycle
			}

			Vertex u = (e.From.seen) ? e.From : e.To;
			Vertex v = e.otherEnd(u);
			v.parent = u;
			wmst += e.Weight;
			v.seen = true;

			for (Edge ve : v.Adj) {
				if (!ve.otherEnd(v).seen) {
					// add only edges that won't create cycle
					pq.insert(ve);
				}
			}
		}
		return wmst;
	}

	/**
	 * Implementation of Prim algorithm using priority queue of Vertices.
	 * Running time: O(|E|*log|V|)
	 * 
	 * @param g
	 *            input graph (assume connected)
	 * @return weight sum of minimum spanning tree
	 */
	public static int PrimMST2(Graph g) {
		// initialization
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			u.d = Infinity;
		}

		int wmst = 0; // weight sum of MST
		Vertex src = g.verts.get(1); // verts[0] is null, pick any vertex
		Vertex comp = new Vertex();// use as comparator argument
		IndexedHeap<Vertex> pq = new IndexedHeap<Vertex>(g.verts.size(), comp);
		/*
		 * parameter d: measures the minimum edge weight that has been seen so
		 * far of the vertex not in the MST to the MST
		 */
		src.d = 0;

		for (Vertex u : g) {
			pq.insert(u); // this will update the index field of u
		}

		while (!pq.isEmpty()) {
			Vertex u = pq.deleteMin();
			u.seen = true;
			wmst += u.d;

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!v.seen && v.d > e.Weight) {
					// a better edge connects v to mst is found
					v.d = e.Weight;
					v.parent = u;
					pq.decreaseKey(v);
				}
			}
		}
		return wmst;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		// Scanner in = new Scanner(System.in);
		Graph g = Graph.readGraph(in, false);

		long start, end;

		start = System.currentTimeMillis();
		System.out.println(PrimMST1(g));
		end = System.currentTimeMillis();
		System.out.println("PrimMST1 running time: " + (end - start) + " ms.");

		start = System.currentTimeMillis();
		System.out.println(PrimMST2(g));
		end = System.currentTimeMillis();
		System.out.println("PrimMST2 running time: " + (end - start) + " ms.");
	}
}
