package com.plow.myoak.model;

import java.util.Date;

public interface Node {

	public String getPath();
	public void setPath(String path);
	public boolean isDirectory();
	public void setDirectory(boolean dir);
	public int getSize();
	public Date getDate();
	
}
