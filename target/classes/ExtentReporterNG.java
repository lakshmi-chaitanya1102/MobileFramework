package resource;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

 
public class ExtentReporterNG implements IReporter {
    private ExtentReports extent;
    ExtentHtmlReporter htmlReporter;
    ExtentTest logger;
 
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\htmlreport.html");
        extent = new ExtentReports();
        
        extent.attachReporter(htmlReporter);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            try {
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
 
                buildTestNodes(context.getPassedTests(), Status.PASS);
                logger.addScreenCaptureFromPath("C:\\SeleniumAutomation\\MobileProject\\Screenshots\\testpass.png");
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                logger.addScreenCaptureFromPath("C:\\SeleniumAutomation\\MobileProject\\Screenshots\\testfail.png");
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
            } catch (IOException e) {
    			e.printStackTrace();
    		}
        }
 
        extent.flush();
        
    }
 
    private void buildTestNodes(IResultMap tests, Status status) {
        ExtentTest test;
 
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.createTest(result.getMethod().getMethodName());
 
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
 
                String message = "Test " + status.toString().toLowerCase() + "ed";
 
                if (result.getThrowable() != null)
                    message = result.getThrowable().getMessage();
 
                test.log(status, message);
 
          //      extent.endTest(test);
            }
        }
    }
 
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();        
    }
    
}