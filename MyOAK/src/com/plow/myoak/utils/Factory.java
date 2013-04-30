package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

import com.plow.myoak.model.Node;
import com.plow.myoak.presentation.contact.ContactPresentation;
import com.plow.myoak.presentation.file.ResourcePresentation;

public class Factory {

	static MenuListProvider menuListProvider;
	public static MenuListProvider getMenuListProvider() {
		return menuListProvider;
	}

	public static List<ResourcePresentation> getResources(Activity context, ResourcePresentation resource) {
		List<ResourcePresentation> resources = new ArrayList<ResourcePresentation>();
		List<Node> nodes = (resource == null) ? EngineUtils.getEngine().ls(EngineUtils.WEBDAV) : EngineUtils.getEngine().ls(EngineUtils.WEBDAV, resource.getResource());
		int i = 0;

		for (Node n : nodes) {
			resources.add(new ResourcePresentation(context, n, i));
			i++;
		}

		return resources;
	}
	
	public static List<ContactPresentation> getContacts(Activity context, ContactPresentation contact) {
		StrictMode.setThreadPolicy(ThreadPolicy.LAX);
		
		List<ContactPresentation> resources = new ArrayList<ContactPresentation>();
		List<Node> nodes = (contact == null) ? EngineUtils.getEngine().ls(EngineUtils.CONTACT) : EngineUtils.getEngine().ls(EngineUtils.CONTACT, contact.getResource());
		int i = 0;

		for (Node n : nodes) {
			resources.add(new ContactPresentation(context, n, i));
			i++;
		}

		return resources;
	}

}
