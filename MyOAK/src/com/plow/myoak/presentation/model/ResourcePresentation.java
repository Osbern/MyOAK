package com.plow.myoak.presentation.model;

import java.util.Date;

import com.plow.myoak.model.Node;

public class ResourcePresentation {
	
	private Node resource;

	protected int icon ;
	protected boolean selected = false;
    
	public ResourcePresentation(Node resource) {
		this.resource = resource;
	}
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public boolean isDirectory() {
		return resource.isDirectory();
	}

	public String getName() {
		return resource.getPath();
	}
	
	public int getSize() {
		return resource.getSize();
	}
	
	public Date getDate() {
		return resource.getDate();
	}
}
