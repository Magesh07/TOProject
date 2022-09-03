package com.qa.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.util.seleniumUtils;
import com.relevantcodes.extentreports.LogStatus;

public class googleSearch extends seleniumUtils {

	@BeforeMethod()
	public void loadBaseUrl(Object[] param) {
		initiazeBrowser();
		test = report.startTest(param[0].toString());
		driver.get(envConfig.getProperty("baseUrl"));

	}

	@Test(dataProvider = "tempTestData")
	public void getSearchCount(String executionFlag, String TestCaseID, String username, String pwd) {
		if (!executionFlag.isEmpty() && executionFlag.equalsIgnoreCase("yes")) {
			sendKeys(By.name("q"), pwd);
			click(By.name("btnK"));

			test.log(LogStatus.PASS, "Navigated to the specified URL");
			List<WebElement> ele = driver.findElements(By.xpath("//*[contains(text(),\"" + username + "\")]"));
			try {
				test.log(LogStatus.PASS, "Navigated to the specified URL " + test.addScreenCapture((capture(driver))));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test.log(LogStatus.INFO, "no of times found - " + String.valueOf(ele.size()));
			System.out.println(ele.size());
			System.out.println("sadfdf" + username);
		} else if (executionFlag.equalsIgnoreCase("skip")) {

		}

	}

	@Test(dataProvider = "tempTestData")
	public void inset_New_Record_Using_APIRequest(String executionFlag, String TestCaseID, String username,
			String pwd) {
		if (!executionFlag.isEmpty() && executionFlag.equalsIgnoreCase("yes")) {

		} else if (executionFlag.equalsIgnoreCase("skip")) {

		}
	}

	@Test(dataProvider = "tempTestData")
	public void update_Record_Using_APIRequest(String executionFlag, String TestCaseID, String username, String pwd) {
		if (!executionFlag.isEmpty() && executionFlag.equalsIgnoreCase("yes")) {

		} else if (executionFlag.equalsIgnoreCase("skip")) {
          //reportStep("","");
		}
	}

}
