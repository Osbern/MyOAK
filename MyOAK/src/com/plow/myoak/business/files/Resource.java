package com.plow.myoak.business.files;

public interface Resource {

	public ResourceType getType();
	public void open();
	public boolean delete();
	public Resource rename();
	
}
