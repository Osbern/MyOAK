package com.plow.myoak.presentation.files;

import java.util.ArrayList;
import java.util.List;

import com.plow.myoak.R;
import com.plow.myoak.business.files.Directory;
import com.plow.myoak.business.files.Resource;

public class DirectoryPresentation extends ResourcePresentation{
	private Directory directory;
	List<ResourcePresentation> childrenPresentations;
    
	public DirectoryPresentation(Directory directory) {
		super(directory);
		icon= R.drawable.folder_icon;
		childrenPresentations = new ArrayList<ResourcePresentation>();
	}

	
	public List<ResourcePresentation> getChildren() {
		List<Resource> children = directory.getChildren();
		childrenPresentations.clear();
		for (Resource resource : children) {
			childrenPresentations.add(new ResourcePresentation(resource));
		}
		return childrenPresentations;
	}

	public Resource createResource() {
		return directory.createResource();
	}

	
}
