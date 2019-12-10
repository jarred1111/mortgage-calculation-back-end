package com.mortageCal.MortageCalculation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.mortageCal.MortageCalculation.entities.MortageInputs;


@Repository
public class CalculationDao {

	public JSONArray findAll() throws JSONException {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MortageCalDB", "root",
				"V2A923!?")) {

			Statement stmt;
			ResultSet rs;
			JSONArray jsonArray = new JSONArray();

			
			String sql = "select * from calculations";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			//Loops through the result set and map it into a JSON array.
			while (rs.next()) {
				
				int total_columns = rs.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
				
				for (int i = 0; i < total_columns; i++) {
					obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
				}
				jsonArray.put(obj);
			}

			return jsonArray;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String insert(MortageInputs MI, String CName, Double MPayments) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MortageCalDB", "root",
				"V2A923!?")) {
			PreparedStatement prepared_statement;

			// Inserts the users information into the table
			String sql_statement = "insert into calculations(cal_name, purchase_price, deposite, bond_term, interest_rate, monthly_payments)"
					+ "values (?,?,?,?,?,?);";

			prepared_statement = conn.prepareStatement(sql_statement);
			prepared_statement.setString(1, CName);
			prepared_statement.setDouble(2, MI.getPurchasePrice());
			prepared_statement.setDouble(3, MI.getDepositePaid());
			prepared_statement.setInt(4, MI.getBondTermYears());
			prepared_statement.setDouble(5, MI.getPercentageRate());
			prepared_statement.setDouble(6, MPayments);
			int result_set = prepared_statement.executeUpdate();

			//if there is a result set then it hasn't failed
			if (result_set == 1) {
				return "Success";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return "Success";
	}
}
