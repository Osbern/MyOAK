package com.plow.myoak.presentation.model;

import java.util.ArrayList;
import java.util.List;

import com.plow.myoak.R;
import com.plow.myoak.engine.EngineImpl;
import com.plow.myoak.model.Directory;
import com.plow.myoak.model.Node;
import com.plow.myoak.utils.EngineUtils;

public class DirectoryPresentation extends ResourcePresentation{
	private Directory directory;
	List<ResourcePresentation> childrenPresentations;
    
	public DirectoryPresentation(Directory directory) {
		super(directory);
		icon= R.drawable.folder_icon;
		childrenPresentations = new ArrayList<ResourcePresentation>();
	}

	
	public List<ResourcePresentation> getChildren() {
		List<Node> children = EngineUtils.getEngine().ls(directory);
		childrenPresentations.clear();
		for (Node resource : children) {
			childrenPresentations.add(new ResourcePresentation(resource));
		}
		return childrenPresentations;
	}

	public Node createResource() {
		return null;//directory.createResource();
	}

	
}
