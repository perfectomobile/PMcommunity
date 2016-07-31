package com.perfectomobile;


import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.appium.java_client.AppiumDriver;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
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
import org.testng.annotations.*;
import org.testng.Assert;


public class NativeExe {

	communityTest test= null;

	private String PERFECTO_HOST        = System.getProperty("np.testHost", "branchtest.perfectomobile.com");
	private String PERFECTO_USER        = System.getProperty("np.testUsername", "test_automation@gmail.com");
	private String PERFECTO_PASSWORD    = System.getProperty("np.testPassword", "Test_automation");

    private static RemoteWebDriver driver;
    private ReportiumClient reportiumClient;

    @Parameters({"platformName" , "model" , "browserName" , "location"})
    @BeforeTest
    public void beforeMethod(String platformName, String model, String browserName, String location) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("user" , PERFECTO_USER);
        capabilities.setCapability("password" , PERFECTO_PASSWORD);
        capabilities.setCapability("platformName" , platformName);
        capabilities.setCapability("model" , model);
        capabilities.setCapability("browserName" , browserName);
        capabilities.setCapability("location" , location);

        driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub") , capabilities);
        driver.manage().timeouts().implicitlyWait(15 , TimeUnit.SECONDS);

        //Create Reportium client.
        reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(
                new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                        .withProject(new Project("Sample Selenium-Reportium" , "1.0"))
                        .withContextTags("Regression") //Optional
                        .withWebDriver(driver) //Optional
                        .build());
    }


    @Test
	public void testDevices() {
        if (driver.getCapabilities().getCapability("platformName").toString().equalsIgnoreCase("android")){
            reportiumClient.testStart("Android-Community",new TestContext("Native EXE"));
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
                reportiumClient.testStop(TestResultFactory.createSuccess());

            } catch (Exception e) {
                reportiumClient.testStop(TestResultFactory.createFailure("Exception encountered",e));
                e.printStackTrace();
            }
        } else{
            reportiumClient.testStart("IOS-Community",new TestContext("Native EXE"));
            try {
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
                reportiumClient.testStop(TestResultFactory.createSuccess());

            } catch (Exception e) {
                reportiumClient.testStop(TestResultFactory.createFailure("Exception encountered",e));
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("Since15")
    @AfterTest
    public void afterMethod(){
        try{
            driver.manage().deleteAllCookies(); //Removes cookies after test.
            driver.quit();
            String reportURL = reportiumClient.getReportUrl();
            System.out.println(reportURL); //Print URL to console

            //TODO: Enable this couple of lines in order to open the browser with the report at the end of the test.
            //if(Desktop.isDesktopSupported())
            //  Desktop.getDesktop().browse(new URI(reportURL));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
