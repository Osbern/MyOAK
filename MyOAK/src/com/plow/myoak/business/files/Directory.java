package com.plow.myoak.business.files;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Directory implements Resource {
 
	private ResourceType type;
	private String name;
	private Resource parent;
	private List<Resource> children;
	private Date date;
	private String size;
    
	
	public Directory(String name, String size, Date date, ResourceType type,Resource parent) {
		super();
		this.type = type;
		this.name = name;
		this.parent = parent;
		children = new ArrayList<Resource>();
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
	
	public List<Resource> getChildren(){
		return children;
		
	}
	
	public Resource createResource(){
		return null;
	}
	
	public Resource getParent(){
		return this.parent;
	}
    
	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public String getSize() {
		return size;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
