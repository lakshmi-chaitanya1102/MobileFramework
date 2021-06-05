package IBM.AppiumFramework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class capability {
	
	protected static String deviceName;
	protected static String apppackage;
	protected static String appActivity;
	public AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	
	public AppiumDriverLocalService startserver()
	{
		boolean flag =checkifserviceRunning(4723);
		if(!flag)
		{
		// Start Appium server
		service=AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
		.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
		.withAppiumJS(new File("C://Users//LakshmiChaitanyaBola//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
		.withIPAddress("127.0.0.1").usingPort(4723));
		service.start();
		}
		return service;
	}
	
	//Check whether service is running or not
	public static boolean checkifserviceRunning(int port)
	{
		boolean isServerRunning =false;
		ServerSocket serversocket;
		try {
			serversocket=new ServerSocket(port);
			serversocket.close();
		}
		catch(IOException e)
		{
			isServerRunning=true;
		}
		finally {
			serversocket=null;
		}
		
		return isServerRunning;
		
	}
	
	public static void startEmulator() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec(System.getProperty("user.dir")+ "\\src\\main\\java\\emulator.bat");
		Thread.sleep(20000);
	}
	

public static AndroidDriver<AndroidElement> capabilities(String deviceName,String apppackage,String appActivity) throws IOException, InterruptedException {

	FileReader fis=new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\global.properties");
	Properties pro=new Properties();
	pro.load(fis);
	deviceName=pro.getProperty("deviceName");
	apppackage=pro.getProperty("apppackage");
	appActivity=pro.getProperty("appActivity");
	
	if(deviceName.contains("Chaitanya"))
	{
		startEmulator();
	}
	
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
				cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, apppackage);
				cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
				cap.setCapability(MobileCapabilityType.NO_RESET, "True");
				
				cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
						"C:\\Users\\LakshmiChaitanyaBola\\Desktop\\FST\\Jars\\chromedriver.exe");
				
				 driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),
						cap);
				return driver;
					
	}


	public static void getScreenshot(String test) throws IOException
	{
		String random = RandomStringUtils.randomAlphabetic(3); 
		File scrfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile,new File("C:\\SeleniumAutomation\\MobileProject\\Screenshots"+"\\"+test+".png"));

	}
}
