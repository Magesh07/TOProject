package com.qa.util;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.output.WriterOutputStream;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import com.qa.base.TestBase;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class seleniumUtils extends TestBase {

//	public static ExtentTest test;
//	protected static ExtentReports report;

	protected static WebDriver driver;
	WebDriverWait wait;
	StringWriter requestWriter;
	StringWriter responseWriter;

	private static final String BROWSER = System.getProperty("browser", "Chrome");

	// Browser configuration - can add more browsers and remote driver here

	public void initiazeBrowser() {
		if (BROWSER.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup(); // can also use set property method for browser executables
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		} else if (BROWSER.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// Setting WebDriverWait with max timeout value of 20 seconds
		wait = new WebDriverWait(driver, 20);

	}

	public void navigateToURL(String URL) {
		System.out.println("Navigating to: " + URL);
		System.out.println("Thread id = " + Thread.currentThread().getId());

	}

	public String getPageTitle() {
		try {
			System.out.print(String.format("The title of the page is: %s\n\n", driver.getTitle()));
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	public WebElement getElement(By selector) {
		try {
			return driver.findElement(selector);
		} catch (Exception e) {
			System.out.println(String.format("Element %s does not exist - proceeding", selector));
		}
		return null;
	}

	public void sendKeys(By selector, String value) {
		WebElement element = getElement(selector);
		clearField(element);
		try {
			element.sendKeys(value);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}
	}

	public void clearField(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}
	}

	public void click(By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			element.click();
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}

	private void waitForElementToBeClickable(By selector) {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));

	}

	public Response postCallWithBody(String baseURI, String resource_Path, Map<String, String> reqHeaders,
			JSONObject formReqBodyContents) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		httpsRequest.headers(reqHeaders);
		httpsRequest.body(formReqBodyContents);
		try {
			// print here the report INFO
			response = httpsRequest.post(resource_Path);
			// print the report line

		} catch (Exception e) {

			// print the report line here
		}
		/*
		 * requestWriter = new StringWriter(); PrintStream requestCaptor = new
		 * PrintStream(new WriterOutputStream(requestWriter),true); responseWriter = new
		 * StringWriter(); PrintStream responseCaptor = new PrintStream(new
		 * WriterOutputStream(responseWriter),true);
		 */

		return response;

	}

	public Response getCallWithParam(String baseURI, String resource_path, Map<String, String> reqHeaders,
			Map<String, String> reqParamters) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		if (reqHeaders == null || reqHeaders.isEmpty() || reqHeaders.size() != 0) {
			httpsRequest.headers(reqHeaders);
		}
		try {
			if (reqParamters == null || reqParamters.isEmpty() || reqParamters.size() != 0) {
				httpsRequest.queryParams(reqParamters);
			}
			 response = httpsRequest.get(resource_path);
			 //print the report here
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//print the failed report FAIL
		}

		return response;
	}
	

}
