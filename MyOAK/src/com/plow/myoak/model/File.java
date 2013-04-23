package com.plow.myoak.model;

import java.util.Date;

public class File implements Node {
	
	private String name;
	private String path;
	private boolean directory;
	private long size;
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
		String[] tmp = path.split("/");
		name = tmp[tmp.length - 1];
	}

	public File(String path, long size, Date date) {
		this(path);
		this.path = path;
		this.size = size;
		this.date = date;
	}

	@Override
	public String getName() {
		return name;
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
	public long getSize() {
		return size;
	}

	@Override
	public Date getDate() {
		return date;
	}
}
