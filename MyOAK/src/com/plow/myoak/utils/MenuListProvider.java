package com.plow.myoak.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MenuListProvider {

	
	
//	private Context context;
	private ArrayList<MenuItem> menu;
	Map<String, MenuItem> item_map;

	public MenuListProvider() {
//		this.context = c;
		setMenu(new ArrayList<MenuItem>());
		item_map = new HashMap<String, MenuItem>();
		menu = new ArrayList<MenuItem>();
		this.createEntries();
	}

	private void createEntries() {

		addItem(new MenuItem("1", "Fichiers"));
		addItem(new MenuItem("2", "Contacts"));
		//addItem(new MenuItem("3", "Calendrier"));
		//addItem(new MenuItem("4", "Musique"));
	}



	private void addItem(MenuItem item) {
		menu.add(item);
		item_map.put(item.id, item);
	}


	public class MenuItem {
		public String id;
		public String content;

		public MenuItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}


	public Map<String, MenuItem> getMap() {
	
		return item_map;
	}

	public ArrayList<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<MenuItem> menu) {
		this.menu = menu;
	}
}
