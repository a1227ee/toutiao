package com.zzx.beans;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class msg {
	
	private  int code;
	private String mag;
	private Map<String, Object> extend=new HashMap<String, Object>();
	public static msg success() {
		msg reusult=new msg();
		reusult.setCode(200);
		reusult.setMag("处理成功");
		return reusult;
	}
	public static msg success(String message) {
		msg reusult=new msg();
		reusult.setCode(200);
		reusult.setMag(message);
		return reusult;
	}
	public static msg fail(String message) {
		msg reusult=new msg();
		reusult.setCode(500);
		reusult.setMag(message);
		return reusult;
	}
	public static msg fail() {
		msg reusult=new msg();
		reusult.setCode(500);
		reusult.setMag("处理失败！");
		return reusult;
	}
	public msg add(String key,Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMag() {
		return mag;
	}
	public void setMag(String mag) {
		this.mag = mag;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}

	
	}
