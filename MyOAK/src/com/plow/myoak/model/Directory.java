package com.plow.myoak.model;

import java.util.Date;

public class Directory implements Node {
	
	private String path;
	private boolean directory;
	private Date date;
	
	public Directory() {
		directory = true;
		path = "";
	}
	
	public Directory(String path) {
		this();
		this.path = path;
	}
	
	public Directory(String path, Date date) {
		this();
		this.path = path;
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
		return 0;
	}
	
	@Override
	public Date getDate() {
		return date;
	}
}
