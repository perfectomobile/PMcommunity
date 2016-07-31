package com.perfectomobile;

public class Device {
	String platform;
	String app;
	String deviceID;
	String persona;
	String applocation;
	
	
	public Device(String platform, String app, String deviceID, String persona, String applocation) {
		super();
		this.platform = platform;
		this.app = app;
		this.deviceID = deviceID;
		this.persona = persona;
		this.applocation = applocation;
	}
	
	@Override public String toString() {
				
		return "{" + platform + "," + app + "," + deviceID + "," + persona + "," + applocation + "}"; 
	};
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	public String getApplocation() {
		return applocation;
	}
	public void setApplocation(String applocation) {
		this.applocation = applocation;
	}

}
