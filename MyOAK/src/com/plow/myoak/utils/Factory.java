package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.plow.myoak.business.files.Directory;
import com.plow.myoak.business.files.File;
import com.plow.myoak.business.files.Resource;
import com.plow.myoak.business.files.ResourceType;
import com.plow.myoak.presentation.files.DirectoryPresentation;
import com.plow.myoak.presentation.files.ResourcePresentation;

public class Factory {

	static MenuListProvider menuListProvider;
	public static MenuListProvider getMenuListProvider(){
		
		
		return menuListProvider;
	}
	
	public static List<ResourcePresentation> getResources(ResourcePresentation resource)   {
		List<ResourcePresentation> resources = new ArrayList<ResourcePresentation>();
		if(resource == null){
			
			Directory root = new Directory("C", "126 Go", new Date(), ResourceType.DIRECTORY, null);
			Directory dir1 = new Directory("Music", "126 Ko", new Date(), ResourceType.DIRECTORY, root);
			Directory dir2 = new Directory("Video", "1206 Mo", new Date(), ResourceType.DIRECTORY, root);
			Directory dir3 = new Directory("Music 1", "126 Ko", new Date(), ResourceType.DIRECTORY, null);
			Directory dir4 = new Directory("Music 2", "126 Ko", new Date(), ResourceType.DIRECTORY, null);
			Directory dir5 = new Directory("Music 3", "126 Ko", new Date(), ResourceType.DIRECTORY, null);
			File banlieuzart1 = new File("banlieuzart 1", "126 Ko", new Date(), ResourceType.DIRECTORY, dir1);
			File banlieuzart2 = new File("banlieuzart 2", "106 Ko", new Date(), ResourceType.DIRECTORY, dir1);
			File banlieuzart3 = new File("banlieuzart 3", "186 Ko", new Date(), ResourceType.DIRECTORY, dir2);
			
			
			resources.add(new DirectoryPresentation(root));
			resources.add(new DirectoryPresentation(dir3));
			resources.add(new DirectoryPresentation(dir4));
			resources.add(new DirectoryPresentation(dir5));
			
		}else{
			if(resource.getType().equals(ResourceType.DIRECTORY)){
				DirectoryPresentation directory = (DirectoryPresentation) resource;
				resources =  directory.getChildren();
			}
		}
		return resources;
	}
	
}
