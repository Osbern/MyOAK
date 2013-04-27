package com.plow.myoak.model;

import java.util.Date;

public class Directory extends Node {
	
	public Directory() {
		super();
		this.directory = true;
	}
	
	public Directory(String path) {
		super(path);
		this.directory = true;
	}
	
	public Directory(String path, Date date) {
		this(path);
		this.date = date;
	}
}
