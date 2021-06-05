package resource;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import IBM.AppiumFramework.capability;


public class Listeners implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//screenshot 
				String s=result.getName();
				try {
				capability.getScreenshot(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//screenshot 
		String s1=result.getName();
		try {
		capability.getScreenshot(s1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}
}
