package testng.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testng.web.utilities.BaseTest;

public class Page1 {

	private WebDriver driver;
	
	@FindBy(xpath = "//*[@alt='Google']")
	public WebElement titleGoogle;

	public Page1(WebDriver driver) {
		this.driver = BaseTest.getDriver();
		PageFactory.initElements(driver, this);
	}

}
