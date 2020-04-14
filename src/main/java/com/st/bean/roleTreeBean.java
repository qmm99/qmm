package com.st.bean;

import java.util.List;

public class roleTreeBean {
	private String id;
	private String title;
	private List<roleTreeBean> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<roleTreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<roleTreeBean> children) {
		this.children = children;
	}
	
}
