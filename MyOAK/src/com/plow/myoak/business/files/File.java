package com.plow.myoak.business.files;

import java.util.Date;


public class File implements Resource {
 
	private ResourceType type;
	private String name;
	private String size;
	private Date date;
	private Resource parent;

	public File(String name, String size, Date date, ResourceType type,Resource parent) {
		super();
		this.type = type;
		this.name = name;
		this.parent = parent;
		if(this.parent != null && parent.getType().equals(ResourceType.DIRECTORY)){
			((Directory) this.parent).getChildren().add(this) ;
		}
		this.date = date;
		this.size = size;
	}
	
	@Override
	public ResourceType getType() {
		return type;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Resource rename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public String getSize() {
		return size;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
