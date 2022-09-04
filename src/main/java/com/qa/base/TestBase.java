package com.qa.base;

import java.io.File;

import com.qa.util.ExcelUtil;
import org.apache.commons.io.FileUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static ExtentTest test;
	protected static ExtentReports report;
	protected static WebDriver driver;
	public static Properties envConfig;
	WebDriverWait wait;

	private static final String BROWSER = System.getProperty("browser", "Chrome");

	@BeforeSuite
	public void suiteSetup() throws Exception {

		// Browser configuration - can add more browsers and remote driver here
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
		// Environment specific properties file loading
		InputStream configFile = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\properties\\" + "test.properties");
		envConfig = new Properties();
		envConfig.load(configFile);
		FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "\\report")); 
		report = new ExtentReports(System.getProperty("user.dir") + "\\report\\ExtentReportResults.html");
	}

	@AfterMethod
	public void screenshotAndDeleteCookies(ITestResult testResult) throws IOException {

		// Deleting cookies
		driver.manage().deleteAllCookies();
		report.endTest(test);
		report.flush();
	}

	@AfterSuite
	public void suiteTearDown() {
		driver.quit();
	}

	@DataProvider(name = "tempTestData")
	public Object[][] tempTestData() throws Exception {

		String[][] testData = ExcelUtil.getExcelDataIn2DArray("src//main//resources//testData//TestData.xlsx", "search");
		return testData;
	}
	
	/*
	 * @DataProvider(name = "tempTestData1") public Object[][] tempTestData1(int
	 * index) throws Exception {
	 * 
	 * System.out.println("INDEX" + index);
	 * 
	 * String[][] testData = ExcelUtil.getExcelDataIn2DArray(
	 * "src//main//resources//testData//TestData.xlsx", "search");
	 * 
	 * return testData; }
	 */
	

	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("src/../report/" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

}
