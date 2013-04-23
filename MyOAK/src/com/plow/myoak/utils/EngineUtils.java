package com.plow.myoak.utils;

import com.plow.myoak.engine.Engine;
import com.plow.myoak.engine.EngineImpl;

public class EngineUtils {
	
	public static String HOST = "http://proj135.istic.univ-rennes1.fr/owncloud/files/webdav.php";
	
	private static Engine engine = null;
	
	public static Engine getEngine() {
		if (engine == null) {
			engine = new EngineImpl();
			engine.connect("Plow", "MoujonBrouh!");
		}
		return engine;
	}

}
