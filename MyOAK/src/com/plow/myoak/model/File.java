package com.plow.myoak.model;

import java.util.Date;

public class File extends Node {
	
	public File() {
		super();
		super.setDirectory(false);
	}
	
	public File(String path) {
		this();
		super.setPath(path);
		String[] tmp = path.split("/");
		super.setName(tmp[tmp.length - 1]);
	}

	public File(String path, long size, Date date) {
		this(path);
		super.setPath(path);
		super.setSize(size);
		super.setDate(date);
	}
}
