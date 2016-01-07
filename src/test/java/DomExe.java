package test.java;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DomExe {


	webCommunity test= null;
	HTMLReporter _rep ;
	@BeforeSuite
	public void BeforeSuite() {
		System.out.println(" *** OpenRep");
		
		Date date =  Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String today = formatter.format(date);
        System.out.println("Today : " + today);
		
		String fileName = "WebCommStats_"+ today +".html";
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
			return util.readFromXmlForWeb();
	}


	//@Parameters({ "deviceID" })
	@Test (dataProvider="Devices" )

	public void testDevices(String platform,String deviceID,String persona, String URL) {
		boolean rc =false;

		test= new webCommunity(_rep,deviceID,platform,URL);



		RemoteWebDriver _RWD = test.getWebDriver();

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

			
			_RWD.close();	
			String repName = "test_comm_"+deviceID;
			util.downloadReport(_RWD, "pdf",repName);	
			_rep.addline(deviceID, String.valueOf(rc)  ,	 	util.getReprtName(repName, true));

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
