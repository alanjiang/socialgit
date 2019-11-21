package com.hobby.gitstat.json;

public class ReqBean {

	 private String email;
	 
	 private String time;//yyyy-mm-dd

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ReqBean [email=" + email + ", time=" + time + "]";
	}
	 
	 
}
