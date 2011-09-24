package com.nofatclips.androidtesting.model;

public interface WidgetState extends WrapperInterface {

	public String getId();
	public void setId(String id);
	public String getName();
	public void setName(String name);
	public String getType();
	public void setType(String type);
	public String getTextType();
	public void setTextType (String inputType);
	public WidgetState clone();
	public String getSimpleType();
	
}