package ca.utoronto.utm.floatingpoint;

public class q1 {
	public static void main(String[] args) {
		q1 p = new q1();
		System.out.println(p.solve711());
	}
	
	/**
	 * Does this attempt (Puzz711.java) to solve the 7.11 problem work? 
	 * Explain why or why not. We will mark this based on the demonstration of your clear understanding of the problem.
	 * A concise, precise explanation is best.
	 * 
	 * This program cannot solve the problem and is ineffidient in terms of time, because:
	 * 1- Some numbers like 0.01f cannot be precisely converted in terms of base 2.
	 * 		Therefore for some numbers only approximate values can used to represent a decimal in binary.
	 * 		Example:  0.1f + 0.1f = 0.2 but 0.1f + 0.1f + 0.1f = 0.30000000000000004
	 * 		From the example above you can see that 0.3f cannot be gotten by adding 0.1f three times. 
	 * 		This is because 0.1 cannot be precisely converted in terms of base 2, thus it is an approximated value.
	 * 		Which is why 0.3f was skipped.
	 * 		This implies that using a float to iterate in a for loop causes some numbers to be skipped.
	 * 		Thus some potential numbers which can solve the equation: (a * b * c * d == 7.11f && a + b + c + d == 7.11f) 
	 * 		can be skipped.
	 * 2- The Program allows for big numbers like 7.01 to be added to small numbers.
	 * 		This however has no bearing on the result of the value.
	 * 		When small float numbers are added, their result is significant.
	 * 		However, when a small value is added to a large value the result equal to the initial large value.
	 * 		Example: 1.0 + 0.00000001 = 1.0, 
	 * 				 0.00000001 + 1.0 = 1.00000001
	 * 				 0.00000001 +0.00000001 = 0.00000002.
	 * 		Therefore the order in which floats are added affects the result.
	 * 3- The Program also allows for the same combination of numbers to be iterated multiple times:
	 * 		To explain further; an instances of when a,b,c,d = 1,2,3,4
	 *   	can occur again where a,b,c,d = 1,3,2,4 or  3,1,2,4 or 4,2,1,4 ...etc.
	 *   	Time is wasted by iterating the same combination of numbers.
	 *   	Therefore having four for loops to iterate every possible value to solve the problem, is heavily inefficient.
	 *  
	 * @return
	 */
	public String solve711() {
		float a, b, c, d;
		for (a = 0.00f; a < 7.11f; a = a + .01f) {
			for (b = 0.00f; b < 7.11f; b = b + .01f) {
				for (c = 0.00f; c < 7.11f; c = c + .01f) {
					for (d = 0.00f; d < 7.11f; d = d + .01f) {
						if (a * b * c * d == 7.11f && a + b + c + d == 7.11f) {
							return (a + " " + b + " " + c + " " + d);
						}
					}
				}
			}
		}
		return "";
	}
}
