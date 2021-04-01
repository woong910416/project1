package com.project1;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BusVO implements Serializable {
	
	public BusVO() {}

	//join() 회원정보
    private static String id;
    private String pw;
    private String name;
    private String jumin;
    
	public static String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
 
    
}