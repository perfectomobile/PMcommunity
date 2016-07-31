package com.perfectomobile;


import io.appium.java_client.AppiumDriver;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;



public class NativeExe {

	communityTest test= null;
	HTMLReporter _rep ;
	@BeforeSuite
	public void BeforeSuite() {
		System.out.println(" *** OpenRep");
		
		Date date =  Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String today = formatter.format(date);
        System.out.println("Today : " + today);
		
		String fileName = "CommStats_"+ today +".html";
		_rep = new HTMLReporter(fileName, "/community/", "Perfecto");

		// add one reporter in the before test will be used by all the tests

	}



	@AfterSuite
	public void AfterSuite(){
		System.out.println("**  End Rep");
		_rep.closeRep();

	}

	// com.united.UnitedCustomerFacingIPhone
	// PackageName: com.android.launcher


	@DataProvider(name = "Devices" , parallel = true)
	public Object[][] testSumInput() throws IOException {
			return util.readFromXml();
	}


	//@Parameters({ "deviceID" })
	@Test (dataProvider="Devices" )

	public void testDevices(String platform,String app,String deviceID,String persona,String applocation) {
		boolean rc =false;

		test= new communityTest(_rep,deviceID,app,platform,persona,applocation);



		AppiumDriver _RWD = test.getWebDriver();

		if (_RWD==null)
		{
			Assert.assertTrue(_RWD==null);
		} 
		
		if (platform.equalsIgnoreCase("ios"))
		{
			rc = test.execI(_RWD);

		}else
		{
			rc = test.execA(_RWD);

		}

		 

		try {

			_RWD.closeApp();
			if(platform.equalsIgnoreCase("ios"))
			{
				_RWD.removeApp("com.bloomfire.enterprise.perfecto");
			}else
			{
				_RWD.removeApp("com.bloomfire.android.perfecto");
			}
			_RWD.close();	
			String repName = "test_comm_"+deviceID;
			util.downloadReport(_RWD, "pdf",repName);	
			_rep.addline(deviceID, String.valueOf(rc), util.getReprtName(repName, true));

		} catch (Exception e) {
		
		}finally
		{
			 try{
				_RWD.quit();

			}catch(Exception e)
			{
				//  driver closed 
			}
			if  (!rc)
			{
				Assert.fail("Test Ended with error");

			}

		}
	}

}
