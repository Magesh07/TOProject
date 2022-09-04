package com.qa.test;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class OppenheimerAPIWrapper {

	public static boolean saveSingleRecord(Hero hero) {

		Gson gson = new Gson();
		String json = gson.toJson(hero);
		System.out.println(json);
		// todo - call actual api to save hero object
		return false;
	}

	public static boolean saveMultipleRecord(ArrayList<Hero> heroList) {

		// todo - call actual api to save hero object
		Gson gson = new Gson();
		String json = gson.toJson(heroList);
		System.out.println(json);
		return false;
	}

	public static boolean verifyNatIDFormat(String natID) {
		boolean result = false;
		int getNatIDlength = natID.length();
		///need to write later
		return result;
	}

	public static boolean verifyTaxRelief(double salary, double taxPaid, int age, String gender) {
		boolean result = false;
		double taxRelief;
		double genderBonus = 0;
		double variable = 0;

		if (gender == "F") {
			genderBonus = 500;
		}
		if (age <= 18) {

		}
		// need to write later
		taxRelief = ((salary - taxPaid) * (variable) + genderBonus);

		return false;

	}

}
