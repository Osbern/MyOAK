package com.plow.myoak.business.files;

import java.util.List;

public class Directory implements Resource {
 

	private Resource parent;
	private List<Resource> children;

	@Override
	public ResourceType getType() {
		// TODO Auto-generated method stub
		return null;
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

	public List<Resource> getChildren(){
		return children;
		
	}
	
	public Resource createResource(){
		return null;
	}
	
	public Resource getParent(){
		return this.parent;
	}
}
