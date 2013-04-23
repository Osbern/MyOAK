package com.plow.myoak.model;

import java.util.Date;

import android.util.Log;

public class Directory implements Node {
	
	private String name;
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
		String[] tmp = path.split("/");
		name = tmp[tmp.length - 1];
		Log.w("Directory", name);
	}
	
	public Directory(String path, Date date) {
		this(path);
		this.path = path;
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
		return 0;
	}
	
	@Override
	public Date getDate() {
		return date;
	}
}
