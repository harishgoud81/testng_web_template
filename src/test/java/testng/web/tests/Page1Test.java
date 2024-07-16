package testng.web.tests;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import testng.web.pages.Page1;
import testng.web.utilities.ActionsHelp;
import testng.web.utilities.BaseTest;
import testng.web.utilities.ExtentReportsClass;

public class Page1Test extends BaseTest{
		
	private static ThreadLocal<Page1> page1 = new ThreadLocal<Page1>();
	
	//Page1 page1 = new Page1(BaseTest.getDriver());
	 @BeforeMethod
	    public void setUp(ITestResult result) {
	        ExtentReportsClass.createTest(result);
	       page1.set(new Page1(getDriver()));
	    }

	
	@Test
	public void program1() throws InterruptedException {
		ActionsHelp.isDIspalyed(page1.get().titleGoogle);
		System.out.println(BaseTest.getDriver().getTitle());
	}
    
	@Test
	public void program2() throws InterruptedException {
		String x = BaseTest.getDriver().findElement(By.xpath("//*[@alt='Googe']")).getAttribute("alt");
		System.out.println(x);
		System.out.println(BaseTest.getDriver().getTitle());
	}
	
	@AfterMethod
	public void quitAll(ITestResult result) {
		page1.remove();
	}
    
}
