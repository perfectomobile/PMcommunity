package com.perfectomobile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.remote.RemoteWebDriver;


/*
 *
 *  Class Name  : HTMLReporter
 *	Author      : Uzi Eilon <uzie@perfectomobile.com>
 *	Date        : Dec 6th 2015  
 *	
 *	Description :
 *	implements ExecutionReporter and create an HTML summary report 
 *	
 */

public class HTMLReporter   {

	BufferedWriter _bw = null;
	public static String SCREENSHOTS_LIB = "/community/";



	public HTMLReporter (String ReportName,String ReportLocation,String title)
	{
		reportHeader(ReportName,ReportLocation,title);
		 

	}

	public void reportHeader (String ReportName,String ReportLocation,String title)
	{
		String repName  = ReportLocation+ ReportName;
		File f = new File (repName) ;

		try {
			_bw = new BufferedWriter(new FileWriter(f));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			_bw.write("<html>");
			_bw.write("<head>");
			_bw.write("<title>Perfecto Report</title>");
			_bw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/rep.css\">");
			_bw.write("</head>");


			_bw.write("<body>");

			// title and static data 
			_bw.write("<div class=\"header\">");
			_bw.write("<img src=\""+SCREENSHOTS_LIB+"/pm-logo-4-uzi.png\" alt=\"logo\" style=\"padding:12px 44px;\" />");

			_bw.write("<h1>Perfecto Mobile Report</h1>");

			_bw.write("<p> Date  :"+dateFormat.format(cal.getTime())+" </p>");
			_bw.write("<p> Test Name: "+title+"</p>");
			_bw.write("</div>");

			_bw.write("<table  border=\"1\" style=\"width:100%\">");
			_bw.write("<tr>");
			_bw.write("<th>device</th>");
			_bw.write("<th>status</th>");
			_bw.write("<th>report</th>");
			_bw.write("</tr>");





		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void closeRep()
	{
		try {
			_bw.write("</table>");  

			_bw.write("</body></html>");

			_bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	public void addScreenShot(RemoteWebDriver w,String name, String location, String deviceID) 
	{
		//	String os = (String) w.getCapabilities().getCapability("platformName");

		//	screenShotData s = new screenShotData(name,location,os,deviceID);
		//	ScreenShortPerAction.put(name, s);
		//	ScreenShortPerDevice.put(deviceID, s);
	}

	public synchronized void addline(String device, String status , String rep)
	{
		try {
			_bw.write("<tr>");
			_bw.write("<td>"+device+"</td>");
			_bw.write("<td>"+status+"</td>");
			if (rep!=null)
			{
 				_bw.write("<td> <a href="+rep+">Report<a></td>");

			}
			else
			{			
				_bw.write("<td> no report </td>");
			}
			_bw.write("</tr>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



}