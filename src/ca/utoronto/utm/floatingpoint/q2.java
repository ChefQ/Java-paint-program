package ca.utoronto.utm.floatingpoint;

import java.util.ArrayList;
import java.util.ArrayList;

/**
 * This class finds the 4 numbers that when multiplied and added together
 * results in 7.11
 *
 */

public class q2 {

	public static void main(String[] args) {
		q2 p = new q2();
		System.out.println(p.solve711());
	}

	/**
	 * This method reduces the number of possibilies to which the for loop has
	 * to iterate to get an expected value for the solution. The for loop also
	 * doesn't iterate using a float, which allows the loop not to skip any
	 * potential number.
	 * 
	 * @return a String of values that satisfies the condition: (a/100.0f *
	 *         b/100.0f * c/100.0f * d/100.0f == 711/100.0f && a/100.0f +
	 *         b/100.0f + c/100.0f + d/100.0f == 711/100.0f)
	 */

	public String solve711() {

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 711; i = i + 1) {
			if (711000000 % i == 0) {
				list.add(i);

			}
		}

		float a, b, c, d;
		a = 1;
		b = 0;
		c = 0;
		d = 0;

		int count = 0;
		int countk = 0;
		int countf = 0;
		int countkg = 0;

		for (count = 0; count < list.size(); count = count + 1) {
			a = list.get(count);
			for (countk = 0; countk < list.size(); countk = countk + 1) {
				b = list.get(countk);
				for (countf = 0; countf < list.size(); countf = countf + 1) {
					c = list.get(countf);
					for (countkg = 0; countkg < list.size(); countkg = countkg + 1) {
						d = list.get(countkg);
						if (a / 100.0f * b / 100.0f * c / 100.0f * d / 100.0f == 711 / 100.0f
								&& a / 100.0f + b / 100.0f + c / 100.0f + d / 100.0f == 711 / 100.0f) {
							return (a / 100.0f + " " + b / 100.0f + " " + c / 100.0f + " " + d / 100.0f);
						}
					}
				}
			}
		}
		return (a + " " + b + " " + c + " " + d);
	}
}
