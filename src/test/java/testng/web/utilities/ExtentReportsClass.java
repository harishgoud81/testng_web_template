package testng.web.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsClass {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static void initReports() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String reportFileName = "ExtentReport_" + timeStamp + ".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter( System.getProperty("user.dir") +"\\target\\ExtentReports\\" + reportFileName);
			// ExtentSparkReporter sparkReporter = new
			// ExtentSparkReporter("target\\ExtentReports\\");
			String className = ITestResult.class.getName();
			sparkReporter.config().setDocumentTitle("TestNG " + className + " Automation Report");
			sparkReporter.config().setReportName("Extent Reports - Test Results");
			sparkReporter.config().setTheme(Theme.DARK); // You can choose Theme.DARK or Theme.STANDARD
			sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
			sparkReporter.config().setCss(".node-name { font-weight: bold; }");
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
		}
	}

	public static void createTest(ITestResult result) {
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String reportFileName = "ExtentReport_" + timeStamp + ".html";
		ExtentTest extentTest = extent.createTest(className + " : " + methodName);
		test.set(extentTest);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void flushReports() {
		if (extent != null) {
			extent.flush();
		}
	}

}
