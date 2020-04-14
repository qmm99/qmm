package com.st.bean;

import java.util.List;

public class JSON {
	private String code="0";
	private String msg="";
	private int count=0;
	private List data=null;
	
	
	public JSON(List data,int count) {
		this.data = data;
		this.count=count;
	}
	
	public JSON(List data) {
		this.data = data;
		if(data.size()>0) {
			this.count=data.size();
		}
	}


	public String getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}


	public int getCount() {
		return count;
	}


	public List getData() {
		return data;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setData(List data) {
		this.data = data;
//		if(data.size()>0) {
//			this.count=data.size();
//		}
	}


	@Override
	public String toString() {
		return "{code=" + code + ", msg=" + msg + ", count=" + count + ", data=" + data + "}";
	}
	
}
