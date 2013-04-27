package Files;

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
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertySet;


public class Main {
	
	private static String host = "http://proj135.istic.univ-rennes1.fr/owncloud/files/webdav.php";
	private static HttpClient client;
	
	public void connect(String login, String pwd) {
		HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(host); 
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 20;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        
        client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials(login, pwd);
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);
	}
	
	public String[] ls() {
		return ls("");
	}
	
	public String[] ls(String path) {
		List<String> res = new ArrayList<String>();
		
		DavMethod pFind;
        MultiStatus multiStatus = null;
        
		try {
			pFind = new PropFindMethod(host + path, DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			client.executeMethod(pFind);
			multiStatus = pFind.getResponseBodyAsMultiStatus();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DavException e) {
			e.printStackTrace();
		}

        for (MultiStatusResponse resp : multiStatus.getResponses()) {
        	String bob = resp.getHref();
        	String[] split = bob.split("/");
        	String tmp = split[split.length - 1];
        	DavPropertySet props = resp.getProperties(200);
        	
        	Date lMod = null;
        	String p = props.get(DavPropertyName.GETLASTMODIFIED).getValue().toString();
            try {
            	lMod = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).parse(p);
            } catch (ParseException e) {
            	e.printStackTrace();
            }
            
            DavProperty prop = props.get(DavPropertyName.GETCONTENTLENGTH);
            
            if (prop != null)
            	tmp += " " + Long.parseLong((String) prop.getValue());
            
            prop = props.get(DavPropertyName.GETCONTENTTYPE);
        	
        	if (prop != null)
        		tmp = "F " + tmp;
        	else
        		tmp = "D " + tmp;
        	
        	tmp += " " + lMod;
        	
        	res.add(tmp);
        }
        return res.toArray(new String[0]);
	}
	
	public String cp(String src, String dest) {
		DavMethod cp = new CopyMethod(host + src, host + dest, true);
        try {
			client.executeMethod(cp);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return cp.getStatusCode() + " "+ cp.getStatusText();
	}
	
	public String rm(String src) {
		DavMethod rm = new DeleteMethod(host + src);
		try {
			client.executeMethod(rm);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rm.getStatusCode() + " " + rm.getStatusText();
	}
	
	public static void main(String[] args) {
        Main pr = new Main();
        pr.connect("Plow", "MoujonBrouh!");
        
        for (String d : pr.ls()) {
        	System.out.println(d);
        }
        
        System.out.println(pr.rm("/Truc/"));
        
		//System.out.println(pr.cp("/bob", "/bob2"));
		
		 for (String d : pr.ls()) {
	        	System.out.println(d);
	     }
        
		System.out.println("\nDISPLAYING BOB:");

		GetMethod get = new GetMethod(
				"http://proj135.istic.univ-rennes1.fr/owncloud/files/webdav.php/bob");
		String buffer = "";

		try {
			client.executeMethod(get);
			buffer = get.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(buffer);
	}

}
