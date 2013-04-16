package com.plow.myoak.engine;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertySet;

import com.plow.myoak.model.Directory;
import com.plow.myoak.model.File;
import com.plow.myoak.model.Node;

public class EngineImpl implements Engine {
	
	private static Engine engine;
	
	public static Engine getInstance() {
		if (engine == null) {
			engine = new EngineImpl();
			engine.connect("Plow", "MoujonBrouh!");
		}
		return engine;
	}
	
	private static String host = "http://proj135.istic.univ-rennes1.fr/owncloud/files/webdav.php";
	private static HttpClient client;

	@Override
	public void connect(String login, String password) {
		HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(host); 
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 20;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        
        client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials(login, password);
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);
	}
	
	@Override
	public List<Node> ls() {
		return ls(null);
	}

	@Override
	public List<Node> ls(Node res) {
		List<Node> result = new ArrayList<Node>();
		String dest = "";
		
		if (res != null)
			dest = res.getPath();
		
		DavMethod pFind;
        MultiStatus multiStatus = null;
        
		try {
			pFind = new PropFindMethod(host + dest, DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			client.executeMethod(pFind);
			multiStatus = pFind.getResponseBodyAsMultiStatus();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DavException e) {
			e.printStackTrace();
		}

		Node tmp;
        for (MultiStatusResponse resp : multiStatus.getResponses()) {
        	String path = resp.getHref().replace("/owncloud/files/webdav.php", "");
        	DavPropertySet props = resp.getProperties(200);
        	
        	Date lMod = null;
        	String p = props.get(DavPropertyName.GETLASTMODIFIED).getValue().toString();
            try {
            	lMod = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).parse(p);
            } catch (ParseException e) {
            	e.printStackTrace();
            }
        	
        	if (props.get(DavPropertyName.RESOURCETYPE).getValue() != null)
        		tmp = new Directory(path);
        	else
        		tmp = new File(path);
        	
        	//tmp += " " + lMod;
        	
        	result.add(tmp);
        }
        return null;
	}

	@Override
	public void cp(Node src, Node dest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rm(Node res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String get(Node res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(String src, Node dest) {
		// TODO Auto-generated method stub
		
	}

}
