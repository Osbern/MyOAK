package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.List;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

import com.plow.myoak.engine.EngineImpl;
import com.plow.myoak.model.Node;
import com.plow.myoak.presentation.model.DirectoryPresentation;
import com.plow.myoak.presentation.model.ResourcePresentation;

public class Factory {

	static MenuListProvider menuListProvider;
	public static MenuListProvider getMenuListProvider(){
		
		
		return menuListProvider;
	}
	
	public static List<ResourcePresentation> getResources(ResourcePresentation resource)   {
		List<ResourcePresentation> resources = new ArrayList<ResourcePresentation>();
		if(resource == null){
			
			StrictMode.setThreadPolicy(ThreadPolicy.LAX);
			
			for (Node n : EngineUtils.getEngine().ls()) {
				resources.add(new ResourcePresentation(n));
			}
			
		}else{
			if(resource.isDirectory()){
				DirectoryPresentation directory = (DirectoryPresentation) resource;
				resources =  directory.getChildren();
			}
		}
		return resources;
	}
	
}
