package IBM.AppiumFramework;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;


public class KhanAcademy extends capability {

	AndroidDriver<AndroidElement> driver;
	
	
	@Test
	public void validateKhanAcademy() throws InterruptedException, IOException {
		
		FileReader fis=new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\global.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String emailId=prop.getProperty("userEmail");
		String password=prop.getProperty("userPassword");
		Thread.sleep(8000);
		service=startserver();
		
		
		driver = capabilities(deviceName, apppackage, appActivity);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElementByClassName("android.widget.Button").click();
		capability.getScreenshot("Sign_in_Page");
		driver.findElementByAccessibilityId("Settings").click();
		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Privacy policy\")")).click();
		driver.findElement(By.id("android:id/button_once")).click();
		
		//Getting number of contexts
		 Set<String> contextNames = driver.getContextHandles();
	        for (String contextName : contextNames) {
	            System.out.println(contextName); 
	        }
	    //Navigating to web view
	    driver.context("WEBVIEW_chrome");
	    Thread.sleep(5000);
	    capability.getScreenshot("Web_App");
	    driver.pressKey(new KeyEvent(AndroidKey.BACK));
	    
	    //Navigating to native view
	    Thread.sleep(2000);
	    driver.context("NATIVE_APP");
	    capability.getScreenshot("Native_App");
	    
	    //Sign in to application
		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Sign in\")")).click();
	    driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Sign in\")")).click();
	    driver.findElementByAccessibilityId("Enter an e-mail address or username").sendKeys(emailId);
	    driver.findElementByAccessibilityId("Password").sendKeys(password);
	    Thread.sleep(2000);
	    driver.findElementByAccessibilityId("Sign in").click();
	    Thread.sleep(4000);
	    capability.getScreenshot("Sign_in_Page_After_Login");
	    
	    
	    //Completing Course
	    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Class 1 math (India)\"));").click();
	    driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Counting small numbers\")")).click();
	    driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Counting with small numbers\")")).click();
	    Thread.sleep(5000);
	    capability.getScreenshot("Video");
	    Thread.sleep(5000);
	    driver.pressKey(new KeyEvent(AndroidKey.BACK));
	    driver.pressKey(new KeyEvent(AndroidKey.BACK));
	    driver.pressKey(new KeyEvent(AndroidKey.BACK));
	  
		//Signout
	    capability.getScreenshot("Before_signout");
	    driver.findElementByAccessibilityId("Settings").click();
	    Thread.sleep(2000);
	    driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Sign out\")")).click();
	    capability.getScreenshot("After_signout");
	    driver.findElement(By.id("android:id/button1")).click();
	 
	 
	    service.stop();
	}

	
}
