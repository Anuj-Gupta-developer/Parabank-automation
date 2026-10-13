package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

// Creates the single ExtentReports instance and keeps track of the test currently being reported.
public class ReportManager {

    private static final String REPORT_PATH = "docs/index.html";

    private static ExtentReports extent;
    private static ExtentTest currentTest;

    // Returns the ExtentReports object, creating and configuring it the first time.
    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
            spark.config().setDocumentTitle("ParaBank Automation Report");
            spark.config().setReportName("ParaBank UI Test Results");
            spark.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application", ConfigReader.get("baseUrl"));
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Headless", ConfigReader.get("headless"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
        return extent;
    }

    // Creates a new entry in the report for a test and remembers it as the current test.
    public static ExtentTest createTest(String name, String description) {
        currentTest = getInstance().createTest(name, description);
        return currentTest;
    }

    // Returns the test entry currently being reported.
    public static ExtentTest getTest() {
        return currentTest;
    }

    // Writes everything to docs/index.html. Must be called once at the end of the run.
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
