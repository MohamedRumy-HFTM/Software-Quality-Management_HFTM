package ch.hftm.rechner;

public class Calculator {

	public double add(double a, double b) {
		return a + b;
	}

	public double subtract(double a, double b) {
		return a - b;
	}

	public double multiply(double a, double b) {
		return a * b;
	}

	public double divide(double a, double b) {
		if (b == 0) {
			throw new ArithmeticException("Division durch null ist nicht erlaubt.");
		}
		return a / b;
	}

}
