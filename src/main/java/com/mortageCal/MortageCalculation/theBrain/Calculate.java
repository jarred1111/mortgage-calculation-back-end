package com.mortageCal.MortageCalculation.theBrain;

import com.google.gson.JsonObject;
import com.mortageCal.MortageCalculation.entities.MortageInputs;

public class Calculate {

	public double Mcalculate(MortageInputs MI) {
		double theRes;
		
		//Used to simplify/break down the calculation
		double a = 1 + (MI.getPercentageRate() / 100 / 12);
		double b = -MI.getBondTermYears() * 12;
		
		//Mortgage bond calculation
		theRes = (  ((MI.getPercentageRate() / 100 / 12) * ( MI.getPurchasePrice() - MI.getDepositePaid())) / (1 - Math.pow(a, b))  );
		
		//Rounding the decimals
		theRes = Math.round(theRes * 100.0) / 100.0;
		
		return theRes;
	}
	
	//Function for working out the amount owed and paid after every payment made
	public JsonObject owedFunc(double monthlyPayments, MortageInputs MI) {
		
		JsonObject response = new JsonObject();
		
		double interestPaid, principalPaid, newBalance, principal;
		double interestPaidHold, principalPaidHold;
		double monthlyInterestRate;
		int month, yearCounter, year;
		int numMonths = MI.getBondTermYears() * 12;

		monthlyInterestRate = MI.getPercentageRate() / 12;
		principal = MI.getPurchasePrice() - MI.getDepositePaid();
		yearCounter = 0;
		year = 1;
		interestPaidHold = 0;
		principalPaidHold = 0;
		
		for (month = 1; month <= numMonths; month++) {
			JsonObject values = new JsonObject();
			
			yearCounter = ++yearCounter;
			
			// Compute amount paid and new balance for each payment period
			interestPaid = principal * (monthlyInterestRate / 100);
			principalPaid = monthlyPayments - interestPaid;
			newBalance = principal - principalPaid;
			
			//If it's not month then it is year
			if(MI.getTimeFrame().equals("Month")) {
				
				
				if(MI.getDataType().equals("currency")) {
					// Insert data into Json Object, per month
					values.addProperty("month", month);
					values.addProperty("interestPaid", interestPaid);
					values.addProperty("principalPaid", principalPaid);
					values.addProperty("newBalance", newBalance);
				} else {
					// Insert data into Json Object, per month
					values.addProperty("month", month);
					values.addProperty("interestPaid", ((interestPaid / monthlyPayments) * 100));
					values.addProperty("principalPaid", ((principalPaid / monthlyPayments) * 100));
					values.addProperty("newBalance", newBalance);
				}
				response.add(Integer.toString(month), values);
				
				
			} else {
				
				if (yearCounter < 12) {
					
					// Total the values for the year 
					interestPaidHold = interestPaidHold + interestPaid;
					principalPaidHold = principalPaidHold + principalPaid;
					
				}
				else {
					
					// Insert data into Json Object, per Year
					if(MI.getDataType().equals("currency")) {
						values.addProperty("month", year);
						values.addProperty("interestPaid", interestPaid);
						values.addProperty("principalPaid", principalPaid);
						values.addProperty("newBalance", newBalance);
					} else {
						values.addProperty("month", year);
						values.addProperty("interestPaid", ((interestPaid / monthlyPayments) * 100));
						values.addProperty("principalPaid", ((principalPaid / monthlyPayments) * 100));
						values.addProperty("newBalance", newBalance);
					}
					
					//Insert it into the response
					response.add(Integer.toString(year), values);
					
					//reset the values
					year = ++year;
					yearCounter = 0;
					interestPaidHold = 0;
					principalPaidHold = 0;
				}
			}

			// Update the balance
			principal = newBalance;
		}
		
		return response;
	}
}
