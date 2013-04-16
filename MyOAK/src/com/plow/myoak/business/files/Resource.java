package com.plow.myoak.business.files;

import java.util.Date;

public interface Resource {

	public ResourceType getType();
	public void open();
	public boolean delete();
	public Resource rename();
	public String getName();
	public Date getDate();
	public String getSize();
	
}
