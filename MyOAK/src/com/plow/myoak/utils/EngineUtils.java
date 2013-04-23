package com.plow.myoak.utils;

import com.plow.myoak.engine.Engine;
import com.plow.myoak.engine.EngineImpl;

public class EngineUtils {
	
	public static String WEBDAV = "http://proj135.istic.univ-rennes1.fr/owncloud/files/webdav.php";
	public static String CONTACT = "http://proj135.istic.univ-rennes1.fr/owncloud/apps/contacts/carddav.php/addressbooks/Plow/contacts";
	
	private static Engine engine = null;
	
	public static Engine getEngine() {
		if (engine == null) {
			engine = new EngineImpl();
			engine.connect("Plow", "MoujonBrouh!");
		}
		return engine;
	}

}
