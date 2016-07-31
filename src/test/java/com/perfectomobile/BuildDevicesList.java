package com.perfectomobile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.client.internal.InternalReportiumClient;
import com.perfecto.reportium.client.internal.InternalReportiumClientFactory;
import com.perfecto.reportium.connection.Connection;
import com.perfecto.reportium.model.*;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class BuildDevicesList {
protected static String host = System.getProperty("np.testHost", "branchtest.perfectomobile.com");

	protected static ReportiumClient reportiumClient;

	@BeforeClass
    public void initReportium() throws URISyntaxException {
        System.out.println("Initializing reportium");
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Selenium-Reportium project" , "1.0")) //Optional
                .withContextTags("Regression" , "SampleTag1" , "SampleTag2") //Optional
                .build();
        MobileInfo i = new MobileInfo();
        i.setManufacturer("Samsung");
        i.setModel("Galaxy S5");
        Platform p = new Platform.PlatformBuilder().withDeviceType(DeviceType.MOBILE).withDeviceInfo(i).withDeviceId("asd").withLocation("local").withOs(OperatingSystem.ANDROID).withOsVersion("1.0").withScreenResolution("800x600").build();
        ExecutionContext execContext = new ExecutionContext.ExecutionContextBuilder().withPlatforms(p).withExternalId("asda").build();
        Connection c = new Connection("eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI2YTJlMzUwNS1iZDhjLTQzYjAtOTczMi01ODFiYmUzZDhmYWUiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNDY5OTU5MDQ2LCJpc3MiOiJodHRwczovL2F1dGguYXdzLXN0Zy5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvYnJhbmNodGVzdCIsImF1ZCI6InJlcG9ydGl1bSIsInN1YiI6ImIwMzIzNzFkLTA5ZTItNDEyMC1hMzkxLWZkYjQ4YjNhNWFjMCIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJyZXBvcnRpdW0iLCJzZXNzaW9uX3N0YXRlIjoiZTk1ZTgwZWItZGVhNy00ZjExLWI1NjgtMTE5ZDhjNzk2YmUyIiwiY2xpZW50X3Nlc3Npb24iOiJhNGIwMzQ2Ny01OTAxLTQ2NzctODQxOS1hMjBhNjcxOTczMjgiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1wcm9maWxlIl19fX0.Tdxb_AkYuU5nj8fOy4K4hrUJG58_qS9Zjxtsa-5P1kmnGqRuq6BalrrfGK2WW8QKTPvfl2QFG_iNeZdXr1r1uPpLbmUEq0E5HcAiuPZM6Yq8Y0lLKe6o96IfT6co4ket65lqS0cQRoNxL9T3pIkpdHPwIkSy35qtdmVEMa-JTuaxKRQKSzlst-4TcMTauG8j_EyOWQWvveMp12IALhpqV06gkRgisPV1syNQJOrh5NgWHpA9DOsB7J0Ya6peVbX6r3cBT8FY0usjsdSfD9lwfhHGYjUb4m7Fw0nI26bR9aguigf1Rrjjk72SQCBaLQGjLepAxinpIzK2LHpj3wqgkQ","branchtest");
        URI reportingServer = new URI("https://reporting.aws-stg.perfectomobile.com");
        URI ssoServer = new URI("https://auth.aws-stg.perfectomobile.com");
        reportiumClient = new InternalReportiumClientFactory().createInternalReportiumClient(c,execContext,reportingServer,ssoServer);
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
