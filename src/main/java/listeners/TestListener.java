package listeners;

import base.DriverFactory;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ReportManager;
import utils.ScreenshotUtil;

import java.util.Arrays;

// TestNG listener: writes each test result to the Extent report and adds a screenshot when a test fails.
public class TestListener implements ITestListener {

    // Runs once before any test: creates the report.
    @Override
    public void onStart(ITestContext context) {
        ReportManager.getInstance();
    }

    // Runs when a test starts: adds an entry to the report.
    @Override
    public void onTestStart(ITestResult result) {
        addTestToReport(result);
    }

    // Runs when a test passes: marks it as passed.
    @Override
    public void onTestSuccess(ITestResult result) {
        ReportManager.getTest().pass("Test passed");
    }

    // Runs when a test fails: logs the error and attaches a screenshot of the browser.
    @Override
    public void onTestFailure(ITestResult result) {
        ReportManager.getTest().fail(result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            ReportManager.getTest().warning("Browser was already closed, no screenshot taken");
            return;
        }
        try {
            String screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver);
            ReportManager.getTest().fail("Screenshot at failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
        } catch (Exception e) {
            ReportManager.getTest().warning("Could not take screenshot: " + e.getMessage());
        }
    }

    // Runs when a test is skipped (e.g. its @BeforeMethod failed): adds it to the report as skipped.
    // onTestStart is not called for these tests, so the entry is created here.
    @Override
    public void onTestSkipped(ITestResult result) {
        addTestToReport(result);
        ReportManager.getTest().skip(result.getThrowable() != null ? result.getThrowable() : new Throwable("Test skipped"));
    }

    // Runs once after all tests: writes the report to docs/index.html.
    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flush();
    }

    // Creates the report entry, adding DataProvider values to the name so each row is distinguishable.
    private void addTestToReport(ITestResult result) {
        String name = result.getMethod().getMethodName();
        if (result.getParameters().length > 0) {
            String[] values = Arrays.stream(result.getParameters())
                    .map(p -> String.valueOf(p).isEmpty() ? "(empty)" : String.valueOf(p))
                    .toArray(String[]::new);
            name += " " + Arrays.toString(values);
        }
        ReportManager.createTest(name, result.getMethod().getDescription());
    }
}
