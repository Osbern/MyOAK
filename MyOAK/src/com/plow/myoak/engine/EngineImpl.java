package com.plow.myoak.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertySet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.plow.myoak.model.Directory;
import com.plow.myoak.model.File;
import com.plow.myoak.model.Node;
import com.plow.myoak.utils.EngineUtils;

public class EngineImpl implements Engine {
	
	private static HttpClient client;

	@Override
	public void connect(String login, String password) {
		HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(EngineUtils.WEBDAV); 
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
	public List<Node> ls(String type) {
		return ls(type, null);
	}

	@Override
	public List<Node> ls(String type, Node res) {
		List<Node> result = new ArrayList<Node>();
		String dest = "";
		
		if (res != null)
			dest = "/" + res.getPath();
		
		DavMethod pFind;
        MultiStatus multiStatus = null;
        
		try {
			pFind = new PropFindMethod(type + dest, DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			client.executeMethod(pFind);
			multiStatus = pFind.getResponseBodyAsMultiStatus();
			int i = 0;
			
			Node tmp;
	        for (MultiStatusResponse resp : multiStatus.getResponses()) {
	        	String path = (i == 0) ? "" : resp.getHref();
	        	DavPropertySet props = resp.getProperties(200);
	        	
	        	Date date = null;
	        	
	        	if (type.equals(EngineUtils.WEBDAV)) {
	        		String p = props.get(DavPropertyName.GETLASTMODIFIED).getValue().toString();
	        		try {
	        			date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).parse(p);
	        		} catch (ParseException e) {
	        			e.printStackTrace();
	        		}
	        	}
	            
	            DavProperty prop = props.get(DavPropertyName.GETCONTENTLENGTH);
	            long size = 0;
	            
	            if (prop != null)
	            	size = Long.parseLong((String) prop.getValue());
	            
	            prop = props.get(DavPropertyName.GETCONTENTTYPE);
	        	
	        	if (prop == null)
	        		tmp = new Directory(path, date);
	        	else
	        		tmp = new File(path, size, date);
	        	
	        	result.add(tmp);
	        	i++;
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DavException e) {
			e.printStackTrace();
		}

        return result;
	}

	@Override
	public String cp(Node src, Node dest) {
		DavMethod cp = new CopyMethod(EngineUtils.WEBDAV + "/" + src.getPath(), EngineUtils.WEBDAV + "/" + dest.getPath(), true);
        try {
			client.executeMethod(cp);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return cp.getStatusCode() + " " + cp.getStatusText();
	}

	@Override
	public String rm(Node res) {
		DavMethod rm = new DeleteMethod(EngineUtils.WEBDAV + "/" + res.getPath());
		Log.w("DELETE", EngineUtils.WEBDAV + res.getPath());
		try {
			client.executeMethod(rm);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rm.getStatusCode() + " " + rm.getStatusText();
	}
	
	@Override
	public String mkdir(String name) {
        DavMethod mkdir = new MkColMethod(EngineUtils.WEBDAV + "/" + name);
        try {
			client.executeMethod(mkdir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mkdir.getStatusCode() + " " + mkdir.getStatusText();
	}

	@Override
	public byte[] get(Node res) {
		GetMethod get = new GetMethod(EngineUtils.WEBDAV + "/" + res.getPath());
		byte[] buffer = null;

		try {
			client.executeMethod(get);
			buffer = get.getResponseBody();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer;
	}

	@Override
	public void put(String src) {
        java.io.File f = new java.io.File(src);
        
        PutMethod method = new PutMethod(EngineUtils.WEBDAV + "/" + f.getName());
        RequestEntity requestEntity;
		try {
			requestEntity = new InputStreamRequestEntity(new FileInputStream(f));
	        method.setRequestEntity(requestEntity);
	        client.executeMethod(method);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void open(Context ctxt, Node n) {
		String name = n.getName();
		String extension = MimeTypeMap.getFileExtensionFromUrl(name);
		byte[] buffer = EngineUtils.getEngine().get(n);
		FileOutputStream outputStream;
		
		try {
		  outputStream = ctxt.openFileOutput(name, Context.MODE_WORLD_READABLE);
		  outputStream.write(buffer);
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		java.io.File file = new java.io.File(ctxt.getFilesDir(), name);
		if (extension.isEmpty())
			extension = "txt";
		String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		
		Intent i = new Intent();
		i.setAction(android.content.Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(file), mimetype);
		ctxt.startActivity(i);
	}

	@Override
	public void stream(final Context ctxt, Node audio) {
		String url= "https://gslb-dl.dropboxusercontent.com/s/hlzpg8px6ymcyq4/00%20-%20Drakwald%20-%20Baldr%27s%20Dream.mp3?token_hash=AAEA59qT4JaH_YgHHQeln3V_tnHj0vWYS0egawKVvbxbIw&dl=1";
		
		Intent i = new Intent();
		i.setAction(android.content.Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse(url), "audio/*");
		ctxt.startActivity(i);
	}

}
