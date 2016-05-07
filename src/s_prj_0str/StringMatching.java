package s_prj_0str;

/**
 * 1, Implement and compare the performances of any of the following algorithms
 * for string matching:
 * 
 * a. Naive
 * 
 * b. Rabin-Karp
 * 
 * c. Knuth-Morris-Pratt
 * 
 * d. Boyer-Moore: http://www.cs.utexas.edu/~moore/publications/fstrpos.pdf (BM
 * algorithm is also discussed in Cormen et al's book, 1st ed, Sec 34.5).
 * 
 * @author Peng Li
 * @author Nan Zhang
 */
public class StringMatching {
	/**
	 * naive implementation of string matching algorithm, best case RT O(n),
	 * worse case RT O(nm)
	 * 
	 * @param text
	 * @param pattern
	 * @return: int - starting index of the first occurance of pattern in text,
	 *          or -1 if not found
	 */
	public static int naive_1(String text, String pattern) {
		if (text == null || pattern == null || text.length() < pattern.length()) {
			return -1;
		}

		int n = text.length();
		int m = pattern.length();

		for (int i = 0; i <= n - m; i++) {
			int j; // must be declared outside of the inner loop!
			for (j = 0; j < m; j++) {
				if (text.charAt(i + j) != pattern.charAt(j)) {
					break;
				}
			}

			if (j == m) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * naive implementation of string matching algorithm, best case RT O(n),
	 * worse case RT O(nm). Explicitly show the backup when match fails
	 * 
	 * @param text
	 * @param pattern
	 * @return: int - starting index of the first occurance of pattern in text,
	 *          or -1 if not found
	 */
	public static int naive_2(String text, String pattern) {
		if (text == null || pattern == null || text.length() < pattern.length()) {
			return -1;
		}

		// i, j must be declared outside of the loop
		int i, n = text.length();
		int j, m = pattern.length();

		for (i = 0, j = 0; i < n && j < m; i++) {
			if (text.charAt(i) == pattern.charAt(j)) {
				j++;
			} else {
				i -= j; // backup
				j = 0;
			}
		}

		if (j == m) {
			return i - m; // i is at the end of the matched string
		}

		return -1;
	}
}
