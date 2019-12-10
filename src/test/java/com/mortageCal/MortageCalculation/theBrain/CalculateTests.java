package com.mortageCal.MortageCalculation.theBrain;

public class CalculateTests {

	public static double Mcalculate(double purchasePrice, double depositePaid, int bondTermYears,double percentageRate) {
		double theRes;
		double a = 1 + (percentageRate / 100 / 12);
		double b = -bondTermYears * 12;
		
		theRes = (  ((percentageRate / 100 / 12) * ( purchasePrice - depositePaid)) / (1 - Math.pow(a, b))  );
		
		return theRes;
	}

	public static void main(String[] args) {
		
		

	}
}
