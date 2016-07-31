package com.perfectomobile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class BuildDeviesList {
protected static String host = System.getProperty("np.testHost", "branchtest.perfectomobile.com");

	protected ReportiumClient reportiumClient;

	@BeforeClass
    public void initReportium(){
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Selenium-Reportium project" , "1.0")) //Optional
                .withContextTags("Regression" , "SampleTag1" , "SampleTag2") //Optional
                .build();
        reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

    @Test
//	public static void main(String[] args) throws IOException {
	public void deviceList () throws IOException {
		reportiumClient.testStart("deviceList",new TestContext("Check devices"));
	    List<Device> listDevices = new ArrayList<Device>();
		System.out.println(host);
		//String devList = getData();
		String iosApp = "com.bloomfire.enterprise.perfecto";
		String AndroidApp = "com.bloomfire.android.perfecto";
		
		String apkLocation = "PUBLIC:PMcommunity\\\\Bloomfire-perfecto-release.apk";
		String ipaLocation = "PUBLIC:PMcommunity\\\\perfecto-enterprise-2.11(20151117.1).ipa";
 		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(getData());

			NodeList handsets = doc.getElementsByTagName("handset");

			for (int temp = 0; temp < handsets.getLength(); temp++) {


				Node handset = handsets.item(temp);

				NodeList handsetData = handset.getChildNodes();
				String id =null ;
				String os = null;
				for (int data = 0; data < handsetData.getLength(); data++) {
					
					
					Node d = handsetData.item(data);
					if (d.getNodeName().equals("deviceId"))
					{
						id = d.getTextContent();
					}
					if (d.getNodeName().equals("os"))
					{
						os = d.getTextContent();
					}
 	 				 
				}
				if (os.equals("iOS"))
				{
					Device d = new Device("ios", iosApp, id, null, ipaLocation);
					System.out.println(d);
					listDevices.add(d);
                    reportiumClient.testStep("Found device " + d);
//					System.out.println("{\"ios\",\""+iosApp+"\",\""+id+"\",null,\""+ipaLocation+"\"},");

				}
				else if ((os.equals("Android")))
				{
					Device d = new Device("Android", AndroidApp, id, null, apkLocation);
					System.out.println(d);
                    reportiumClient.testStep("Found device " + d);
					listDevices.add(d);
				}
				else
				{
					// does not support window or BB
				}
			 
				 
			}

		} catch (Exception e) {
			System.out.println("can't parse XML ");
			reportiumClient.testStop(TestResultFactory.createFailure("Failure",e));
            e.printStackTrace();
		}
		util.writeToXml(listDevices);
		System.out.println(host);
        reportiumClient.testStop(TestResultFactory.createSuccess());
		
	}
	

	private static InputStream getFdata()
	{
		File initialFile = new File("target/handsets.xml");
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			return targetStream;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;	
		}
	}


	private static InputStream getData() {
		try {
			
			String URLDevices =  "https://" + host + "/services/handsets?operation=list&user=test_automation@gmail.com&password=Test_automation&status=connected";
			System.out.println (URLDevices);
			URL obj = new URL(URLDevices);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			return con.getInputStream();
		
		} catch (Exception e) {
			System.out.println("cant get data ");
			return null;
		}		
	}

}
