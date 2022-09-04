package com.qa.test;

import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.util.ExcelUtil;
import com.qa.util.seleniumUtils;

public class OppenheimerTest extends seleniumUtils {

	@BeforeMethod()
	public void loadBaseUrl(Object[] param) {
		// initiazeBrowser();
		test = report.startTest(param[0].toString());
		// driver.get(envConfig.getProperty("baseUrl"));
	}

	@Test(dataProvider = "tempTestData")
	public void insertSingleRecord(String TestCaseID, String NatID, String Name, String Gender, String Birthday,
			String Salary, String TaxPaid)  {

		/*
		 * String TestCaseID = ""; String NatID =""; String Name =""; String Gender="";
		 * String Birthday =""; String Salary =""; String TaxPaid ="";
		 * 
		 * String[][] testData = ExcelUtil.getExcelDataIn2DArray(
		 * "src//main//resources//testData//TestData2.xlsx", "search");
		 * 
		 * System.out.println("I am inside Test -obj" + testData);
		 */

		if (TestCaseID.equalsIgnoreCase("TC1")) {
			System.out.println("I am inside Test1");
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			System.out.println("Name+++++++++++" + Name);
			Hero hero = new Hero(NatID, Name, Gender, Birthday, Double.parseDouble(Salary),
					Double.parseDouble(TaxPaid));
			OppenheimerAPIWrapper apiWrapper = new OppenheimerAPIWrapper();
			boolean result = apiWrapper.saveSingleRecord(hero);

		}
	}

	@Test(dataProvider = "tempTestData")
	public void insertMultipleRecord(String TestCaseID, String NatID, String Name, String Gender, String Birthday,
			String Salary, String TaxPaid) {

		ArrayList<Hero> heroList = new ArrayList<>();
		if (TestCaseID.equalsIgnoreCase("TC2")) {
			System.out.println("I am inside Test2");
			// System.out.println( "I am inside if");
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			System.out.println("Name+++++++++++" + Name);
			Hero hero = new Hero(NatID, Name, Gender, Birthday, Double.parseDouble(Salary),
					Double.parseDouble(TaxPaid));
			heroList.add(hero);
			OppenheimerAPIWrapper apiWrapper = new OppenheimerAPIWrapper();
			boolean result = apiWrapper.saveMultipleRecord(heroList);

		}
		System.out.println("Arrary List count" + heroList.size());
	}
}
