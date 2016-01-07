package test.java;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.util;

import io.appium.java_client.AppiumDriver;

public class webCommunity {
	

	RemoteWebDriver _rwd;
	String _deviceID;
	String browser;
	String _location;
	HTMLReporter _rep;
	
	public webCommunity(HTMLReporter reporter,String deviceId,String platform, String browser)
	{
		_rep=reporter;
//		String deviceId,String browser,String platform, HTMLReporter _rep
		_rwd = util.getRWD(deviceId, browser, platform, reporter);
		}

	public RemoteWebDriver getWebDriver() {
		return _rwd;
	}

	public String getDeviceID() {
		return _deviceID;
	}



	public  boolean execI(RemoteWebDriver  driver)   {
		
		

		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
			driver.get("community.perfectomobile.com");
			driver.findElementByCssSelector("html > body:nth-of-type(1) > header.primary-header.translates-with-nav > button.primary-nav-button.js-primary-nav-button").click();
			//switchToContext(driver, "WEBVIEW");
			driver.findElementByXPath("//*[text()=\"Log In\"]").click();
		 	driver.findElement(By.xpath("((//input[@id='user_email'])[1]")).sendKeys("uzie@perfectomobile.com");
		 	driver.findElement(By.xpath("((//input[@id='user_password'])[1]")).sendKeys("Perfecto1");
		 	driver.findElement(By.xpath("(//*[text()='Log In']")).click();
//		 	use the search button to filter for Appium posts
		 	driver.findElementByXPath("//*[@class='search-button js-search-button']").click();
		 	driver.findElementByXPath("//*[@class=\"search-input js-search-input\"]").sendKeys("Appium");
//		 	cancel the filter
		 	driver.findElementByXPath("//*[@class='search-cancel-button js-search-cancel-button']").click();
		 	driver.findElementByCssSelector("#new_user > div.box.box_rounded > div.box__footer > input[name=\"commit\"]").click();
		 	driver.findElementByCssSelector("html > body:nth-of-type(1) > header.primary-header.translates-with-nav > button.primary-nav-button.js-primary-nav-button").click();
		 	
		 	try{
//			check that Uzi logged in
		 	String greeting = driver.findElementByXPath("//*[@class=\"greeting\"]").getAttribute("text");
		 	Assert.assertEquals(greeting, "Hi,Uzi!");
		 	} catch (Exception e) {
		 		System.out.println("Uzi is not logged in");
				// TODO: handle exception
			}
		 	driver.findElementByXPath("//*[@class=\"icon ic-handle-down\"]").click();
		 	driver.findElementByXPath("//*[text()=\"Sign Out\"]").click();
		 	driver.close();

			
		} 
			catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
		
	} 

	public  boolean execA(RemoteWebDriver  driver)   {

		try {
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.get("community.perfectomobile.com");
			driver.findElementByCssSelector("html > body:nth-of-type(1) > header.primary-header.translates-with-nav > button.primary-nav-button.js-primary-nav-button").click();
			driver.findElement(By.xpath("(//*[text()='Log In']")).click();
			driver.findElement(By.xpath("((//input[@id='user_email'])[1]")).sendKeys("uzie@perfectomobile.com");
		 	driver.findElement(By.xpath("((//input[@id='user_password'])[1]")).sendKeys("Perfecto1");
		 	driver.findElement(By.xpath("(//*[@class='submit button button_sign-in sign-in button_full']")).click();
		 	driver.findElementByXPath("//*[@class='search-button js-search-button']").click();
		 	driver.findElementByXPath("//*[@class='search-input js-search-input']").sendKeys("appium");
		 	driver.findElementByXPath("//*[@class='search-cancel-button js-search-cancel-button']").click();
		 	driver.findElementByXPath("//*[@class='primary-nav-button js-primary-nav-button']").click();
		 	try{
//				check that Uzi logged in
			 	String greeting = driver.findElementByXPath("//*[@class=\"greeting\"]").getAttribute("text");
			 	Assert.assertEquals(greeting, "Hi,Uzi!");
			 	} catch (Exception e) {
			 		System.out.println("Uzi is not logged in");
					// TODO: handle exception
				}

		 	driver.findElementByXPath("//*[@class=\"icon ic-handle-down\"]").click();
		 	driver.findElementByXPath("//*[text()=\"Sign Out\"]").click();
		 	driver.close();





			
			
//			driver.findElement(By.xpath("//android.widget.EditText[1]")).sendKeys("uzie@perfectomobile.com");
//			driver.findElement(By.xpath("//android.widget.EditText[2]")).sendKeys("Perfecto1");
//			driver.findElement(By.xpath("//android.widget.Button[@text='Log In']")).click();
//			driver.findElement(By.xpath("//*[@resource-id='com.bloomfire.android.perfecto:id/home_screen_filter']")).click();
//			driver.findElement(By.xpath("//*[@text='Clear Filters']")).click();
//			driver.findElement(By.xpath("//*[@text='filter by category']")).click();
//			driver.findElement(By.xpath("(//android.widget.CheckBox)[2]")).click();
//		//checks the second check box
//			driver.findElement(By.xpath("(//android.widget.CheckBox)[4]")).click();
//			driver.findElement(By.xpath("(//*[@text='Done'])[1]")).click();
//			util.sleep(500);	
//			driver.findElement(By.xpath("(//*[@text='Done'])[1]")).click();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	} 



}
