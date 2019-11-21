package com.hobby.gitstat.json;

public class GitReqBean {

	
	/*{

        url:'YOURT_GIT_URL", -- 必填

        branch: "YOUR_GIT_BRANCH",  --GIT分支， 可选，为空代表所有的分支 

        user: "GIT_USER_ACCOUNT", --  GIT用户帐号， 可选，为空代表所有的用户

        time: " yyyyMMdd:yyyyMMdd",    --  时间段, 可选， 为空代表所有的时间， 格式为 起始时间年月日，

        yyyyMMdd: yyyyMMdd

    }*/
	
	private String url,branch,user,time;
	
	private int lines;//提交代码行

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		return "GitReqBean [url=" + url + ", branch=" + branch + ", user=" + user + ", time=" + time + ", lines="
				+ lines + "]";
	}

	
	
	

}
