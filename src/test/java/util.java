package test.java;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.xstream.XStream;


public class util {

	public static String REPORT_LIB = "/community/";
	public static String SCREENSHOTS_LIB = "/community/";
	protected static String user = "admint";
	protected static String password = "admin";
	protected static String host = System.getProperty("np.testHost", "branchtest.perfectomobile.com");
	
	public static void closeTest(RemoteWebDriver driver)
	{
		System.out.println("CloseTest");
		driver.quit();
	}

	

	
//	public static RemoteWebDriver getRWD(String deviceId,String browser,String platform, HTMLReporter _rep)   {
		public static RemoteWebDriver getRWD(String deviceId, String browser, String platform, HTMLReporter reporter) {	
		RemoteWebDriver  webdriver = null;

		if (deviceId.equals("local"))
		{
			DesiredCapabilities capabilities =  DesiredCapabilities.firefox();
			capabilities.setCapability("platformName",  platform);


			try {
				webdriver = new RemoteWebDriver(new URL("http://54.68.7.220:4444//wd/hub"), DesiredCapabilities.firefox());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block

				capabilities.setCapability("platformName",  platform);

				e.printStackTrace();
			}

		}
		else
		{
			DesiredCapabilities capabilities = new DesiredCapabilities(browser, "", Platform.ANY);

			
			capabilities.setCapability("user", user );
			capabilities.setCapability("password", password);
			capabilities.setCapability("deviceName",  deviceId);
			capabilities.setCapability("platformName",  platform);
			
//			String host = null;
//			capabilities.setCapability("host", host);
			

			//capabilities.setCapability("takesScreenShot", false);
			//capabilities.setCapability("automationName", "PerfectoMobile");
			try {
				webdriver = new  AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub") , capabilities);
			} catch (Exception e) {
				String ErrToRep = e.getMessage().substring(0,e.getMessage().indexOf("Command duration")-1);
				System.out.println(ErrToRep);
				return (null);


			}
		}
		return webdriver;

	}
//	public static RemoteWebDriver getRWD(String deviceId,String app,String platform,String persona,String appLocation,HTMLReporter rep) {
//		
//		return getRWD(platform,"https://" + host , user , password ,persona,rep);
//	}
//	public static RemoteWebDriver getRWD(String deviceId,String platform,String Cloud,String user,String password,String persona,HTMLReporter rep) throws MalformedURLException{
//		
//		
//		String browserName = "mobileOS";
//		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
//		capabilities.setCapability("user", user);
//		capabilities.setCapability("password", password);
//		capabilities.setCapability("deviceName", deviceId);
//		
////		capabilities.setCapability("platformName", platform);
//	
//		RemoteWebDriver webdriver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
//		
//		return webdriver;
//	}

	public static AppiumDriver getAppiumDriver(String deviceId,String app,String platform,String persona,String appLocation,HTMLReporter rep)   {
		return getAppiumDriver(deviceId,app,platform,"https://" + host , user , password ,persona,appLocation,rep);
	}
	public static AppiumDriver getAppiumDriver(String deviceId,String app,String platform,String Cloud,String user,String password,String persona,String appLocation,HTMLReporter rep)   {

		AppiumDriver webdriver= null;

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
		


		// get local Appium webDriver
		if (platform=="local")
		{
			capabilities.setCapability("deviceName",  deviceId);
			capabilities.setCapability("app-activity","com.thehartford.hignavigator.MainActivity");
			capabilities.setCapability("appPackage","com.thehartford.hignavigator");
			try {
				webdriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return webdriver;

		}

		// PerfectoDriver
		if (platform.equalsIgnoreCase("ios"))
		{
			capabilities.setCapability("bundleId", app);
			capabilities.setCapability("automationName", "appium");


		}else
		{
			capabilities.setCapability("app-activity",app);
			capabilities.setCapability("appPackage",app);

		}

		if (persona != null)
		{
			capabilities.setCapability("windTunnelPersona", persona);
		}
		if (appLocation!= null)
		{
			capabilities.setCapability("app", appLocation);
		}
		capabilities.setCapability("user",  user);
		capabilities.setCapability("password", password);
		capabilities.setCapability("deviceName",  deviceId);
		//capabilities.setCapability("platformName",  platform);


		//capabilities.setCapability("takesScreenShot", false);
		//capabilities.setCapability("automationName", "PerfectoMobile");
		try {
			webdriver = new AndroidDriver(new URL(Cloud+"/nexperience/perfectomobile/wd/hub") , capabilities);
		} catch (Exception e) {
			String ErrToRep = e.getMessage().substring(0,e.getMessage().indexOf("Command duration")-1);
			System.out.println(ErrToRep);
			if (rep !=null)
			{
				rep.addline(deviceId, ErrToRep, null);
			}
			return (null);


		}

		return webdriver;

	}

	public static String getScreenShot(RemoteWebDriver driver,String name,String deviceID )
	{
		String screenShotName = SCREENSHOTS_LIB+name+"_"+deviceID+".png";
		driver   = (RemoteWebDriver) new Augmenter().augment( driver );
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(screenShotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenShotName;
	}

	public static void startApp(String appName,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", appName);
		d.executeScript("mobile:application:open", params);
	}


	public static void stoptApp(String appName,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", appName);
		d.executeScript("mobile:application:close", params);
	}

	public static void setLocation(String address,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("address", address);
		d.executeScript("mobile:location:set", params);
	}
	 
	public static void setLocationCoordinates(String latlong,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("coordinates", latlong);
		d.executeScript("mobile:location:set", params);
	}

	public static void pressKey(String key,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("keySequence", key);
		d.executeScript("mobile:presskey:", params);
	}
	 

	public static void longtouch(String key,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("location", key);  // 145,449
		params.put("duration", key);  
		d.executeScript("mobile:touch:tap", params);
	}

	 
	public static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}
	public static void swipe(String start,String end,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("start", start);  //50%,50%
		params.put("end", end);  //50%,50%

		d.executeScript("mobile:touch:swipe", params);

	}

	public static void rotateDevice (String stat,WebDriver d )
	{
		// operation - next or reset
		Map<String,String> params = new HashMap<String,String>();
		params.put("operation", stat);
		((RemoteWebDriver) d).executeScript("mobile:handset:rotate", params);
	}



	public static void downloadReport(RemoteWebDriver driver, String type, String fileName) throws IOException {
		try { 
			String command = "mobile:report:download"; 
			Map<String, Object> params = new HashMap<>(); 
			params.put("type", "html"); 
			String report = (String)driver.executeScript(command, params); 
			File reportFile = new File(getReprtName(fileName, true) ); 
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(reportFile)); 
			byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report); 
			output.write(reportBytes); output.close(); 
		} catch (Exception ex) { 
			System.out.println("Got exception " + ex); }
	}

	public static String getReprtName(String repID,boolean withPath) {
		if (withPath)
		{
			return REPORT_LIB+"/rep_"+repID+".html";
		}
		else
		{
			return  "/rep_"+repID+".html";
		}

	}
	public static void pointOfInterest(RemoteWebDriver d ,String poiName, String poiStatus) {
		String command;
		Map<String,Object> params = new HashMap<String,Object>();
		command = "mobile:status:event";
		params.put("description", poiName);
		params.put("status", poiStatus);
		Object result = d.executeScript(command, params);
	}
	public static long timerGet(RemoteWebDriver d,String timerType) {
		String command = "mobile:timer:info";
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", timerType);
		long result = (long)d.executeScript(command, params);
		return result;
	}
	public static void timerReport(RemoteWebDriver d,long timerResult, int threashold, String description, String name) {
		String command;
		Map<String,Object> params = new HashMap<String,Object>();
		command = "mobile:status:timer";
		params.put("result", timerResult);
		params.put("threshold", threashold);
		params.put("description", description);
		//params.put("status", status);
		params.put("name", name);
        Object result = d.executeScript(command, params);

		
	}
	private static byte[] readFile(File path) throws FileNotFoundException, IOException {
		int length = (int)path.length();
		byte[] content = new byte[length];
		InputStream inStream = new FileInputStream(path);
		try {
			inStream.read(content);
		}
		finally {
			inStream.close();
		}
		return content;
	}

	// =============================== private methods / xml helper  =========================================

    public static String writeToXml (List<Device> list) throws IOException{
           XStream xstream = new XStream();
           String xml = null;
           try {
   			xml = xstream.toXML(list);
         File newTextFile = new File("C:/community/t.xml");
         FileWriter fw = new FileWriter(newTextFile);
         fw.write(xml);
         fw.close();

          }
           catch (IOException iox) {
                  
          }
           System.out.println("file created");
           return xml;
    }
    
    public static String[][] readFromXml() throws IOException{
           XStream xstream = new XStream();
           String xml = null;
           BufferedReader br = new BufferedReader(new FileReader("c:/community/t.xml"));
           try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
   		     xml = sb.toString();
           }
           catch (IOException e) {
                  e.printStackTrace();
           } 
            finally {
                br.close();
           }
           
           List<Device> DevicesList = (List<Device>)xstream.fromXML(xml);
           String[][] tabArray = null;
           tabArray=new String[DevicesList.size()][5];
           for (int i = 0; i < DevicesList.size(); i++) {
        	   tabArray[i][0] = DevicesList.get(i).getPlatform();
               tabArray[i][1] = DevicesList.get(i).getApp();
               tabArray[i][2] = DevicesList.get(i).getDeviceID();
               tabArray[i][3] = DevicesList.get(i).getPersona();
               tabArray[i][4] = DevicesList.get(i).applocation;
           }
         
         return tabArray;
    }
    
    public static String[][] readFromXmlForWeb() throws IOException{
        XStream xstream = new XStream();
        String xml = null;
        BufferedReader br = new BufferedReader(new FileReader("c:/community/t.xml"));
        try {
             StringBuilder sb = new StringBuilder();
             String line = br.readLine();
             while (line != null) {
                 sb.append(line);
                 sb.append(System.lineSeparator());
                 line = br.readLine();
             }
		     xml = sb.toString();
        }
        catch (IOException e) {
               e.printStackTrace();
        } 
         finally {
             br.close();
        }
        
        List<Device> DevicesList = (List<Device>)xstream.fromXML(xml);
        String[][] tabArray = null;
        tabArray=new String[DevicesList.size()][4];
        for (int i = 0; i < DevicesList.size(); i++) {
     	   tabArray[i][0] = DevicesList.get(i).getPlatform();
     	   tabArray[i][1] = DevicesList.get(i).getDeviceID();
     	   tabArray[i][2] = "community.perfectomobile.com";
     	   tabArray[i][3] = DevicesList.get(i).getPersona();
         }
      
      return tabArray;
 }
 

		public static void sleep(long millis) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
			}
		}




		




	





		





	
	}
