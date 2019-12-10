package com.mortageCal.MortageCalculation.endpoints;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.mortageCal.MortageCalculation.dao.CalculationDao;
import com.mortageCal.MortageCalculation.entities.MortageInputs;
import com.mortageCal.MortageCalculation.theBrain.Calculate;

@RestController
public class API {
	
	@Autowired
	private static final Logger logger = LogManager.getLogger(MortageInputs.class);
	  
	  @RequestMapping(value = "/mortageCalculator", method = RequestMethod.POST)
	  public ResponseEntity<String> mortageCalculator(@RequestBody Map<String, Object> data) {
		  
		  MortageInputs MI = null;
		  
		  //If the data is in the incorrect format will throw an error
		  try {
				MI = new MortageInputs(
						Double.parseDouble(data.get("purchasePrice").toString().trim()),
						Double.parseDouble(data.get("deposite").toString().trim()), 
						Integer.parseInt(data.get("bondTerm").toString().trim()), 
						Double.parseDouble(data.get("interestRate").toString().trim()),
						data.get("timeFrame").toString().trim(),
						data.get("dataType").toString().trim()
						);
		  } catch(Exception e) {				
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your data is incorrect");
		  }
			
			Calculate cal = new Calculate();
			
			//log the new user
			MI.log();
			
			//get the value of the monthly payments
			double theRes = cal.Mcalculate(MI);
			
			//Get the break down per month
			JsonObject monthAnn = cal.owedFunc(theRes, MI);
			
			//adds the values to a JSON object
			JsonObject response = new JsonObject();
			response.addProperty("YourMPayment", theRes);
			response.add("theMonthAdd", monthAnn);
			
			logger.info("[API] API, called. returning without failour." + response);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response.toString());
	  }
	  
	  @RequestMapping(value = "/calSave", method = RequestMethod.POST)
	  public ResponseEntity<String> calSave(@RequestBody Map<String, Object> data) {
		  
		  MortageInputs MI = null;
		  
		  //If the data is in the incorrect format, will throw an error
		  try {
				MI = new MortageInputs(
						Double.parseDouble(data.get("purchasePrice").toString().trim()),
						Double.parseDouble(data.get("deposite").toString().trim()), 
						Integer.parseInt(data.get("bondTerm").toString().trim()), 
						Double.parseDouble(data.get("interestRate").toString().trim()),
						data.get("timeFrame").toString().trim(),
						data.get("dataType").toString().trim()
						);
		  } catch(Exception e) {				
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your data is incorrect");
		  }
			
			//log the new user
			MI.log();
			
			Calculate cal = new Calculate();
			CalculationDao calDao = new CalculationDao();
			
			//get the value of the monthly payments
			double theRes = cal.Mcalculate(MI);
			
			try {
				String res = calDao.insert(MI, data.get("calName").toString(), theRes);
				
				//If it returns null there was an issues with the data.
				if(res == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate names. Please select a new one.");
				}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database error");
			}
			
			//adds the values to a JSON object
			JsonObject response = new JsonObject();
			response.addProperty("Message", "Your calculation has been saved as " + data.get("calName"));
			
			logger.info("[API] API, called. Saved the calculation." + response);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response.toString());
	  }
	  
	  @RequestMapping(value = "/calList", method = RequestMethod.GET)
	  public ResponseEntity<String> calList() {
		  
			CalculationDao calDao = new CalculationDao();

			JSONArray response = new JSONArray();
			
			try {
				response = calDao.findAll();
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database error");
			}
			
			
			logger.info("[API] API, called. Saved the calculation." + response);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response.toString());
	  }
	  
}

