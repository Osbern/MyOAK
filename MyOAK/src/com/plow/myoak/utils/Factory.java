package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

import com.plow.myoak.model.Node;
import com.plow.myoak.presentation.model.ResourcePresentation;

public class Factory {

	static MenuListProvider menuListProvider;
	public static MenuListProvider getMenuListProvider() {
		return menuListProvider;
	}

	public static List<ResourcePresentation> getResources(Activity context, ResourcePresentation resource) {
		StrictMode.setThreadPolicy(ThreadPolicy.LAX);
		
		List<ResourcePresentation> resources = new ArrayList<ResourcePresentation>();
		List<Node> nodes = (resource == null) ? EngineUtils.getEngine().ls() : EngineUtils.getEngine().ls(resource.getResource());
		int i = 0;

		for (Node n : nodes) {
			resources.add(new ResourcePresentation(context, n, i));
			i++;
		}

		return resources;
	}

}
