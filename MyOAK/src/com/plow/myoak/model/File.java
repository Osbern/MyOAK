package com.plow.myoak.model;

import java.util.Date;

public class File extends Node {
	
	public File() {
		super();
		this.directory = false;
	}
	
	public File(String path) {
		super(path);
		this.directory = false;
	}

	public File(String path, long size, Date date) {
		this(path);
		this.size = size;
		this.date = date;
	}
}
