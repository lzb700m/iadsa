package s_prj_1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Suppose large numbers are stored in a list of integers. Write functions for
 * adding and subtracting large numbers.
 * 
 * @author Peng Li
 */

public class LargeNumber {
	/**
	 * add 2 large integer represented by linkedlist
	 * 
	 * @param x
	 *            input integer
	 * @param y
	 *            input integer
	 * @param z
	 *            resulting integer for addition operation
	 * @param b
	 *            base of integer
	 */
	public static void add(List<Integer> x, List<Integer> y, List<Integer> z,
			int b) {

		Iterator<Integer> itX = x.iterator();
		Iterator<Integer> itY = y.iterator();
		Integer intX = nextHelper(itX);
		Integer intY = nextHelper(itY);
		Integer carry = 0;

		while (intX != null && intY != null) {
			z.add((intX + intY + carry) % b);
			carry = (intX + intY + carry) / b;
			intX = nextHelper(itX);
			intY = nextHelper(itY);
		}

		while (intX != null) {
			z.add((intX + carry) % b);
			carry = (intX + carry) / b;
			intX = nextHelper(itX);
		}

		while (intY != null) {
			z.add((intY + carry) % b);
			carry = (intY + carry) / b;
			intY = nextHelper(itY);
		}

		if (carry != 0) {
			z.add(carry);
		}

		System.out.println(z);
	}

	/**
	 * subtract one large integer represented by linkedlist by another (assume x
	 * >= y)
	 * 
	 * @param x
	 *            integer to be subtracted from
	 * @param y
	 *            integer to be subtracted
	 * @param z
	 *            resulting integer for subtraction operation
	 * @param b
	 *            base of integer
	 */
	public static void subtract(List<Integer> x, List<Integer> y,
			List<Integer> z, int b) {
		Iterator<Integer> itX = x.iterator();
		Iterator<Integer> itY = y.iterator();
		Integer intX = nextHelper(itX);
		Integer intY = nextHelper(itY);
		Integer borrow = 0;
		boolean needsBorrow = false;

		while (intX != null && intY != null) {
			needsBorrow = intX + borrow < intY;
			z.add(needsBorrow ? (intX + borrow + b - intY)
					: (intX + borrow - intY));
			borrow = needsBorrow ? -1 : 0;
			intX = nextHelper(itX);
			intY = nextHelper(itY);
		}

		while (intX != null) {
			needsBorrow = intX + borrow < 0;
			z.add(needsBorrow ? (intX + borrow + b) : (intX + borrow));
			borrow = needsBorrow ? -1 : 0;
			intX = nextHelper(itX);
		}

		// get rid of the leading zeros
		ListIterator<Integer> itBackward = z.listIterator(z.size());
		while (itBackward.previous() == 0) {
			itBackward.remove();
		}

		System.out.println(z);
	}

	private static Integer nextHelper(Iterator<Integer> it) {
		return (it.hasNext()) ? it.next() : null;
	}

	public static void main(String[] args) {
		List<Integer> x = new LinkedList<>();
		List<Integer> y = new LinkedList<>();
		List<Integer> z = new LinkedList<>();

		x.add(9);
		x.add(0);
		x.add(0);
		x.add(0);
		x.add(1);

		y.add(9);
		y.add(9);

		System.out.println("x is " + x);
		System.out.println("y is " + y);
		System.out.print("x + y is ");
		add(x, y, z, 10);
		z.clear();
		System.out.print("x - y is ");
		subtract(x, y, z, 10);
	}
}
