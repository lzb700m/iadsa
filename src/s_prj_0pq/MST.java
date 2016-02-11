package s_prj_0pq;

import java.util.Scanner;

import common.Graph;
import common.Vertex;

public class MST {
	static final int Infinity = Integer.MAX_VALUE;

	static int PrimMST(Graph g) {
		int wmst = 0;
		Vertex src = g.verts.get(1);

		// Code for Prim's algorithm needs to be written

		return wmst;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readGraph(in, false);
		System.out.println(PrimMST(g));
	}
}
