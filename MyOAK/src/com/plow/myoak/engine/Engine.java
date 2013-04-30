package com.plow.myoak.engine;

import java.util.List;

import android.content.Context;

import com.plow.myoak.model.Node;

public interface Engine {
	
	public void connect(String login, String password);
	public List<Node> ls(String type);
	public List<Node> ls(String type, Node node);
	public String cp(Node src, Node dest);
	public String rm(Node node);
	public String mkdir(String name);
	public byte[] get(Node res);
	public void put(String src);
	public void open(final Context ctxt, Node n);
	public void stream(final Context ctxt, Node audio);

}
