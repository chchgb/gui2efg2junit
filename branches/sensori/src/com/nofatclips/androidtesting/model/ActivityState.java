package com.nofatclips.androidtesting.model;

import java.util.ArrayList;

public interface ActivityState extends WrapperInterface, Iterable<WidgetState> {
	
	public static String FAILURE = "fail";
	public static String CRASH = "crash";
	public static String EXIT = "exit";
	
	public String getName();
	public void setName (String name);
	public String getTitle();
	public void setTitle(String title);
	public String getId();
	public void setId (String id);
	public String getUniqueId();
	public void setUniqueId (String id);
	public String getDescriptionId();
	public void setDescriptionId (String id);
	public String getScreenshot();
	public void setScreenshot (String screenshot);
	public void addWidget (WidgetState widget);
	public boolean hasWidget (WidgetState widget);
	public boolean isExit();
	public boolean isCrash();
	public boolean isFailure();
	public void markAsExit();
	public void markAsCrash();
	public void markAsFailure();

	/** @author nicola amatucci */
	public ArrayList<SupportedEvent> getSupportedEvents();
	public ArrayList<SupportedEvent> getSupportedEventsByWidgetUniqueId(String uid);
	public void resetSupportedEvents();
	public void addSupportedEvent(SupportedEvent event);
	public boolean supportsEvent(String uid, String event);
	
	//public void setUsesSensorsManager(boolean yes_no);
	//public boolean getUsesSensorsManager();
	//public void setUsesLocationManager(boolean yes_no);
	//public boolean getUsesLocationManager();
	/** @author nicola amatucci */

}
