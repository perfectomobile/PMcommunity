package com.perfectomobile;

	 

	import io.appium.java_client.AppiumDriver;
	import io.appium.java_client.android.AndroidDriver;

	import java.net.URL;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.concurrent.TimeUnit;


	import org.openqa.selenium.By;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.remote.RemoteWebDriver;
	import org.testng.Assert;



	public class communityTest {
		AppiumDriver _wd;
		String _deviceID;
		String _location;
		HTMLReporter _rep;
		public communityTest(HTMLReporter reporter,String deviceID,String appName,String platform,String persona,String appLocation)
		{
			_rep=reporter;

			_wd = util.getAppiumDriver(deviceID,appName,platform,persona, appLocation,_rep);
 		}

		public communityTest(String deviceID,String appName,String platform,String persona,String appLocation){
			_wd = util.getAppiumDriver(deviceID,appName,platform,persona, appLocation,_rep);
		}



		public AppiumDriver getWebDriver() {
			return _wd;
		}

		public String getDeviceID() {
			return _deviceID;
		}



		public  boolean execI(AppiumDriver  driver)   {

			try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				try {
					driver.findElement(By.xpath("//UIAButton[@name='OK']")).click();
				
				} catch (Exception e) {
					// TODO: handle exception
				}
			 	driver.findElement(By.xpath("(//UIATextField)[2]")).clear();

			 	driver.findElement(By.xpath("(//UIATextField)[2]")).sendKeys("uzie@perfectomobile.com");
			  	driver.findElement(By.xpath("(//UIASecureTextField)[2]")).sendKeys("Perfecto1");
			 	driver.findElement(By.xpath("(//UIAButton[@name='Log in'])[2]")).click();
			 	driver.findElement(By.xpath("(//UIAStaticText[@name='Perfecto Mobile Community'])[1]")).click();

				//logout 
				WebElement w = driver.findElement(By.xpath("//UIAImage[@name='hub-tab']"));
 
				//String x = w.getAttribute("X");
				//String y = w.getAttribute("Y");

				util.swipe("50%,5%","50%,50%",driver);
				 driver.findElement(By.xpath("//UIAButton[@name=\"ic logout\"]")).click();
				 driver.findElement(By.xpath("//UIAButton[@name=\"Yes\"]")).click();

				
				
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}

			return true;


		} 
		public  boolean execA(AppiumDriver  driver)   {

			try {
				driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
				driver.findElement(By.xpath("//android.widget.EditText[1]")).sendKeys("uzie@perfectomobile.com");
				driver.findElement(By.xpath("//android.widget.EditText[2]")).sendKeys("Perfecto1");
				driver.findElement(By.xpath("//android.widget.Button[@text='Log In']")).click();
				driver.findElement(By.xpath("//*[@resource-id='com.bloomfire.android.perfecto:id/home_screen_filter']")).click();
				driver.findElement(By.xpath("//*[@text='Clear Filters']")).click();
				driver.findElement(By.xpath("//*[@text='filter by category']")).click();
				driver.findElement(By.xpath("(//android.widget.CheckBox)[2]")).click();
			//checks the second check box
				driver.findElement(By.xpath("(//android.widget.CheckBox)[4]")).click();
				driver.findElement(By.xpath("(//*[@text='Done'])[1]")).click();
				util.sleep(500);	
				driver.findElement(By.xpath("(//*[@text='Done'])[1]")).click();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			return true;

		} 

	}

