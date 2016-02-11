package s_prj_0pq;

/**
 * 
 * @author Peng Li Priority Queue interface definition
 * 
 * @param <T>
 *            object to be stored in Priority Queue
 */

public interface PQ<T> {
	public void insert(T x);

	public T deleteMin();

	public T min();

	public void add(T x); // equivalent to insert(T x)

	public T remove(); // equivalent to deleteMin()

	public T peek(); // equivalent to min()
}
