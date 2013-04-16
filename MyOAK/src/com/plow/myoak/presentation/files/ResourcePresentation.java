package com.plow.myoak.presentation.files;

import com.plow.myoak.business.files.Resource;
import com.plow.myoak.business.files.ResourceType;

public class ResourcePresentation {
	
	private Resource resource;

	protected int icon ;
	protected boolean selected = false;
    
	public ResourcePresentation(Resource resource) {
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

	public ResourceType getType() {
		return resource.getType();
	}

	public void open() {
		resource.open();
	}

	public boolean delete() {
		return resource.delete();
	}

	public Resource rename() {
		return resource.rename();
	}

	public String getName() {
		return resource.getName();
	}
	
	
	public String getDate() {
		return resource.getDate().toString();
	}

	public String getSize() {
		return resource.getSize();
	}
	
	
	
}
