package com.plow.myoak.model;

import java.util.Date;

public class File implements Node {
	
	private String path;
	private boolean directory;
	private int size;
	private Date date;
	
	public File() {
		directory = false;
		path = "";
		size = 0;
		date = null;
	}
	
	public File(String path) {
		this();
		this.path = path;
	}

	public File(String path, int size, Date date) {
		this();
		this.path = path;
		this.size = size;
		this.date = date;
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean isDirectory() {
		return directory;
	}

	@Override
	public void setDirectory(boolean dir) {
		directory = dir;
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Date getDate() {
		return date;
	}

}
