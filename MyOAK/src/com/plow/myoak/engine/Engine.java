package com.plow.myoak.engine;

import java.util.List;

import com.plow.myoak.model.Node;

public interface Engine {
	
	public void connect(String login, String password);
	public List<Node> ls();
	public List<Node> ls(Node node);
	public void cp(Node src, Node dest);
	public void rm(Node node);
	public String get(Node res);
	public void put(String src, Node dest);

}
