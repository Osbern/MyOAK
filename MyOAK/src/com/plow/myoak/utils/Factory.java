package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.jackrabbit.webdav.property.ResourceType;

import com.plow.myoak.model.Directory;
import com.plow.myoak.model.File;
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
			
			Directory root = new Directory("C",  new Date());
			Directory dir1 = new Directory("Music",new Date());
			Directory dir2 = new Directory("Video", new Date());
			Directory dir3 = new Directory("Music 1", new Date());
			Directory dir4 = new Directory("Music 2", new Date());
			Directory dir5 = new Directory("Music 3", new Date());
			File banlieuzart1 = new File("banlieuzart 1", 126, new Date());
			File banlieuzart2 = new File("banlieuzart 2", 106, new Date());
			File banlieuzart3 = new File("banlieuzart 3", 186, new Date());
			
			
			resources.add(new DirectoryPresentation(root));
			resources.add(new DirectoryPresentation(dir3));
			resources.add(new DirectoryPresentation(dir4));
			resources.add(new DirectoryPresentation(dir5));
			
		}else{
			if(resource.isDirectory()){
				DirectoryPresentation directory = (DirectoryPresentation) resource;
				resources =  directory.getChildren();
			}
		}
		return resources;
	}
	
}
