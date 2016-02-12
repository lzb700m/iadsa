package s_prj_0pq;

/**
 * Subclass for BinaryHeap that include update index function of the element
 * 
 * @author Peng Li
 */
// Ver 1.0:  Wed, Feb 3.  Initial description.
// Ver 1.1:  Thu, Feb 11.  Simplified Index interface

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
	/** Build a priority queue with a given array q */
	IndexedHeap(T[] q, Comparator<T> comp) {
		super(q, comp);
	}

	/** Create an empty priority queue of given maximum size */
	IndexedHeap(int n, Comparator<T> comp) {
		super(n, comp);
	}

	/** restore heap order property after the priority of x has decreased */
	public void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}

	@Override
	public void assign(int i, T x) {
		super.assign(i, x);
		/*
		 * NOTE: if line 31 is replaced with pq[i] = x, a ClassCastException
		 * will be thrown. Java complained something like
		 * "could not cast Object to Index"
		 */
		x.putIndex(i);
	}
	//
	// public static void main(String[] args) throws FileNotFoundException {
	// Scanner in = new Scanner(System.in);
	// Graph g = Graph.readGraph(in, false);
	// Vertex comp = new Vertex();
	//
	// PQ<Vertex> pq = new IndexedHeap<Vertex>(g.verts.size(), comp);
	//
	// int i = 100;
	// for (Vertex v : g) {
	// v.d = i--;
	// }
	//
	// for (Vertex v : g) {
	// pq.insert(v);
	// }
	//
	// for (Vertex v : g) {
	// System.out.println(v.index + ", " + v.d);
	// }
	// }
}
