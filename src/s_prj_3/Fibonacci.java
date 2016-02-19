package s_prj_3;

import common.Timer;

/**
 * Implementation of Fibonicci number calculator for both linear and divide and
 * conquer algorithms
 * 
 * @author Peng Li
 *
 */
public class Fibonacci {
	private static final long MOD_DIVISOR = 999953;
	private static final Timer timer = new Timer();

	/**
	 * Naive implementation of Fibonacci calculator, running time O(n)
	 * 
	 * @param n
	 *            a number of whose Fibonacci needs to be calculated
	 * @param p
	 *            modulo divisor to avoid overflow
	 * @return nth Fibonacci number mod p
	 */
	public static long linearFibonacci(long n, long p) {

		long[] fibs = new long[3];
		fibs[0] = 0;
		fibs[1] = 1;
		if (n == 0) {
			// corner case not covered by the finite state machine
			return fibs[0];
		}
		/*
		 * state of finite state machine, we only needs to remember the last 2
		 * Fibonacci number to calculate the next one, state indicates which
		 * number in the array needs to be updated next. this saves space in
		 * case n is very large
		 */
		int state = 1;

		for (long i = 2; i <= n; i++) {
			state = (state + 1) % 3;
			fibs[state] = (fibs[(state + 1) % 3] + fibs[(state + 2) % 3]) % p;
		}

		return fibs[state];
	}

	/**
	 * Divide and conquer implementation of Fibonacci, running time O(logn)
	 * 
	 * @param n
	 *            a number of whose Fibonacci needs to be calculated
	 * @param p
	 *            modulo divisor to avoid overflow
	 * @return nth Fibonacci number mod p
	 */
	public static long logFibonacci(long n, long p) {
		if (n == 0) {
			// corner case not handled by matrix form
			return 0;
		}
		// signature matrix for Fibonacci matrix form
		long[][] signature = { { 1, 1 }, { 1, 0 } };
		// column vector contains first 2 Fibonacci number
		long[][] v1 = { { 1 }, { 0 } };

		// column vector contains the nth and the (n-1)th Fibonicci number
		long[][] vn = matrixMultiply(power(signature, n - 1, p), v1, p);
		return vn[0][0];
	}

	/**
	 * Calculate the power of a matrix
	 * 
	 * @param x
	 *            an input square matrix
	 * @param n
	 *            exponential number of the power calculation
	 * @param p
	 *            modulo divisor to avoid overflow
	 * @return an square matrix representing x to the power of n
	 */
	private static long[][] power(long[][] x, long n, long p) {
		// simple input check
		if (x.length != x[0].length) {
			return null;
		}

		int l = x.length;
		long[][] ret = new long[l][l];

		if (n == 0) {
			// base case 1: return identity matrix
			for (int i = 0; i < l; i++) {
				ret[i][i] = 1;
			}
			return ret;
		} else if (n == 1) {
			// base case 1: return x itself
			return x;
		} else {
			// divide and conquer
			ret = power(matrixMultiply(x, x, p), n / 2, p);
			if (n % 2 == 0) {
				return ret;
			} else {
				return (matrixMultiply(ret, x, p));
			}
		}
	}

	/**
	 * Calculate the multiplication of two matrices, fast for sparse matrices
	 * (skip multiplication operation for zero values)
	 * 
	 * @param a
	 *            multiplicand matrix
	 * @param b
	 *            multiplier matrix
	 * @param p
	 *            modulo divisor to avoid overflow
	 * @return product of a and b
	 */
	private static long[][] matrixMultiply(long[][] a, long[][] b, long p) {
		// simple input check
		if (a[0].length != b.length) {
			return null;
		}

		int m = a.length;
		int n = b[0].length;
		int mn = a[0].length;
		long[][] ret = new long[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < mn; j++) {
				if (a[i][j] != 0) {
					// skip zero values in a
					for (int k = 0; k < n; k++) {
						if (b[j][k] != 0) {
							// skip zero values in b
							ret[i][k] += (a[i][j] * b[j][k]) % p;
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Driver program to test implementation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		timer.start();
		System.out.println(linearFibonacci(1000000000, MOD_DIVISOR));
		timer.end();
		System.out.println(timer);

		timer.start();
		System.out.println(logFibonacci(1000000000, MOD_DIVISOR));
		timer.end();
		System.out.print(timer);
	}
}