package com.plow.myoak.model;

import java.util.Date;

public abstract class Node {
	
	private String name;
	private String path;
	private boolean directory;
	private long size;
	private Date date;
	
	public Node() {
		name = "";
		path = "";
		directory = false;
		size = 0;
		date = null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name.equals("webdav.php"))
			name = "..";
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
