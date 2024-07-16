package testng.web.utilities;

import org.openqa.selenium.WebElement;

public class ActionsHelp {

	public static boolean isDIspalyed(WebElement element) {
		boolean bool = false;
		try {
			bool = element.isDisplayed();
			ExtentReportsClass.getTest().pass("The element is Displayed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
}
