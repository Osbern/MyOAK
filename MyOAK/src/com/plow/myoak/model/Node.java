package com.plow.myoak.model;

import java.util.Date;

public interface Node {

	public String getPath();
	public String getName();
	public void setPath(String path);
	public boolean isDirectory();
	public void setDirectory(boolean dir);
	public long getSize();
	public Date getDate();
	
}
