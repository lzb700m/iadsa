package common;

/**
 * Class to represent a vertex of a graph
 * 
 * @author Peng Li
 * add class variable cno, start, finish for connected component number
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import s_prj_0pq.Index;

public class Vertex implements Comparator<Vertex>, Index {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList
	public int cno; // connected component number
	public int start; // start time in DFS visit
	public int finish; // end time in DFS visit

	public int index; // index in the priority queue
	public int d; // priority in the priority queue

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	public Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
	}

	/**
	 * Empty Vertex object used as comparator instance
	 */
	public Vertex() {

	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

	@Override
	public void putIndex(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public int compare(Vertex o1, Vertex o2) {
		return o1.d - o2.d;
	}
}
