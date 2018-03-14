package ca.utoronto.utm.floatingpoint;


public class q3 {
	/**
	 * YOUR ANSWER GOES HERE!!!
	 * 
	 * a) -6.8 as an IEEE754 single is
	 * 
	 * To convert -6.8 to the IEEE7S4 single point standard
	 *  The IEEE754 breaks the number into parts
	 *  [sign bit][exponent - 8 bits][mentissa - 23 bits]
	 *  
	 *  the number is negative so the sign bit is 1
	 *  6 in binary is 110
	 *  0.8 in binary is 110011001100...
	 *  
	 *  6.8 = 110.110011001100...
	 *  We move the decimal to be infront of the first non-zero digit
	 *  
	 *  it becomes 1.10110011001100... * 2^(2)
	 *  
	 *  The decimal value of the exponent is 127 + 2 = 129
	 *  
	 *  129 in binary exponent is 10000001
	 *  
	 *  Since, the digits after the point follow an infinite recursive pattern, we have to round the number
	 *  The mentissa can only contain 23 bits, so we fill it with the first 23 digits after the point
	 *  
	 *  1[10000001]10110011001100110011001(100.....)
	 *  
	 *  To round, we look at the first 3 digits after the 23rd digit 
	 *  In this case, we have 100 come after the 23rd digit so we round up
	 *  We round up by adding 1 to the mentissa from the 23rd digit 
	 *  
	 *  Final answer ---> 1[10000001]10110011001100110011010
	 * 
	 * b) 23.1 as an IEEE754 single is 
	 * 
	 *  The IEEE754 breaks the number into parts
	 *  [sign bit][exponent - 8 bits][mentissa - 23 bits]
	 *  
	 *  the number is positive so the sign bit is 0
	 *  23 in binary is 10111
	 *  0.1 in binary is 0.00011001100...
	 *  
	 *  23.1 = 10111.00011001100...
	 *  We move the decimal to be infront of the first non-zero digit
	 *  
	 *  it becomes 1.01100011001100... * 2^(4)
	 *  
	 *  The decimal value of the exponent is 127 + 3 = 131
	 *  
	 *  131 in binary exponent is 10000011
	 *  
	 *  Since, the digits after the point follow an infinite recursive pattern, we have to round the number
	 *  The mentissa can only contain 23 bits, so we fill it with the first 23 digits after the point
	 *  
	 *  0[10000011]01110001100110011001100(110.....)
	 *  
	 *  To round, we look at the first 3 digits after the 23rd digit 
	 *  In this case, we have 110 come after the 23rd digit so we round up
	 *  We round up by adding 1 to the mentissa from the 23rd digit 
	 *  
	 *  Final answer ---> 0[10000011]01110001100110011001101 
	 *  
	 *  
	 * 
	 * 
	 * 
	 *
	 */
}
