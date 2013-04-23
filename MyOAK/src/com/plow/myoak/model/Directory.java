package com.plow.myoak.model;

import java.util.Date;

public class Directory extends Node {
	
	public Directory() {
		super();
		super.setDirectory(true);
	}
	
	public Directory(String path) {
		this();
		super.setPath(path);
		String[] tmp = path.split("/");
		super.setName(tmp[tmp.length - 1]);
	}
	
	public Directory(String path, Date date) {
		this(path);
		super.setPath(path);
		super.setDate(date);
	}
}
