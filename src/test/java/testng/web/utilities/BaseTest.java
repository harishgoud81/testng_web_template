package testng.web.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	ConfigReader config = new ConfigReader();

	@BeforeClass
	public void beforeSuite() {
		ExtentReportsClass.initReports();
	}

	public void setUp(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		}
		driver.get().get(config.getProperty("stageurl"));
		driver.get().manage().window().maximize();
		driver.get().manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
			  File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			  try {
				  String dest = System.getProperty("user.dir") + "\\target\\screenshots\\" + screenshotName + ".png";
		            FileUtils.copyFile(screenshot, new File(dest));
		            return dest;
		        } catch (IOException e) {
		            System.out.println(e.getMessage());
		            return null;
		        }
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		ExtentTest test = ExtentReportsClass.getTest();
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = captureScreenshot(driver.get(), result.getName());
			if (screenshotPath != null && !screenshotPath.isEmpty()) {
				try {
					test.addScreenCaptureFromPath(screenshotPath);
					test.fail("Test Failed");
				} catch (Exception e) {
					test.fail("Test Failed but screenshot could not be captured: " + e.getMessage());
				}
			} else {
				test.fail("Test Failed but screenshot could not be captured");
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test Skipped");
		}
		quitDriver();
	}

	@BeforeMethod
	public void initiateBrowser(ITestResult result) {
		setUp(config.getProperty("browser"));
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	@AfterClass
	public void flushReportsAfterClass() {
		// quitDriver();
		ExtentReportsClass.flushReports();
	}

}