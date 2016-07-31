package com.perfectomobile;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

public class NativeAppExeBasic {

	public static void main(String[] args) {

   	 String deviceID = "CEA5D5FBEAC16BA80707F8B5F370F24A08DBAC97";
	 String platform = "ios";

	//	String deviceID = "06157DF643532240";
	// timimg issue print on diffrent location 	
	// String deviceID = "HT54CSV03077";
	 
		// can not instal table Note + "Allow Google ....DECLINE ACCEPT"
	// String deviceID = "52003C354EAE122D";
	
		// stream black error - element not found 
	//	String deviceID = "4B9BD305";
		 
	//	 String deviceID = "CD9BE4E0";
		 
		 
	//	 String deviceID = "06157DF6AD8A2531";
 
		 
	//	String platform = "Android";

	 HTMLReporter rep = new HTMLReporter("CommStats.html", "/Users/tall/community/", "Perfecto");
 
	 communityTest test= new communityTest(rep,deviceID,"com.bloomfire.enterprise.perfecto",platform,null,"PUBLIC:PMcommunity\\perfecto-enterprise-2.11(20151117.1).ipa");
	//	communityTest test= new communityTest(deviceID,"com.bloomfire.android.perfecto",platform,null,"PUBLIC:PMcommunity\\Bloomfire-perfecto-release.apk");

		AppiumDriver _RWD = test.getWebDriver();
		try {
			if (_RWD==null)
			{
				rep.addline(deviceID, "Error: not RWD", null);

				Assert.assertTrue(_RWD==null);
			}
			
			boolean b;
			if (platform.equalsIgnoreCase("ios"))
			{
				b = test.execI(_RWD);
				

			}else
			{
				b = test.execA(_RWD);

			}

			//close the test  
			_RWD.closeApp();
			_RWD.close();	
			String repName = "test_comm_"+deviceID+".html";
			util.downloadReport(_RWD, "pdf",repName);	
			rep.addline(deviceID, String.valueOf(b)  ,	 	util.getReprtName(repName, true));
			rep.closeRep();
		} catch (IOException e) {

			e.printStackTrace();

		}finally
		{
			String devId = (String) _RWD.getCapabilities().getCapability("deviceName");
			String os = (String) _RWD.getCapabilities().getCapability("platformName");

			System.out.println("********************** close >"+devId+" os:  "+os);
			try{
				_RWD.quit();

			}catch(Exception e)
			{
				//  driver closed 
			}
			 

		} 
	}
}
