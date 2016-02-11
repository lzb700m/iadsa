package common;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {
	/**
	 * Generate a list of random integer and save it to file
	 * 
	 * @param count
	 *            number of integers needs to be generated
	 * @param range
	 *            maximum value of the integer generated
	 * @throws IOException
	 *             thrown if the file write operation fails
	 */
	public static void generateInt(int count, int range) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("./data/");
		sb.append("integer_" + count + ".txt");
		File file = new File(sb.toString());
		sb.delete(0, sb.length());

		for (int i = 0; i < count; i++) {
			int randomInt = (int) (Math.random() * range + 1);
			sb.append(randomInt + " ");
		}

		BufferedWriter bf = new BufferedWriter(new FileWriter(file));
		bf.write(sb.toString());
		bf.close();
	}

	/**
	 * Print the content of an array
	 * 
	 * @param arr
	 *            input array
	 * @return a String with content of the element of the input array separated
	 *         by comma
	 */
	public static <T> String printArray(T[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i].toString() + ", ");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.deleteCharAt(sb.length() - 1);
		sb.append(";");
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		generateInt(10, 100);
	}
}
