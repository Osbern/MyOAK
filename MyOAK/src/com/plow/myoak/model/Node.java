package com.plow.myoak.model;

import java.util.Date;

import android.util.Log;

public abstract class Node {
	
	public static String prefix = "owncloud/files/webdav.php";
	
	protected String name;
	protected String path;
	protected boolean directory;
	protected long size;
	protected Date date;
	
	public Node() {
		name = "";
		path = "";
		directory = false;
		size = 0;
		date = null;
	}
	
	public Node(String path) {
		this();
		if (path.equals("")) {
			this.path = path;
			this.name = "..";
		} else {
			this.path = path.replace(prefix, "");
			String[] tmp = path.split("/");
			this.name = tmp[tmp.length - 1];
		}
		Log.w("NODE", "name:" + this.name + " | path:" + this.path);
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
