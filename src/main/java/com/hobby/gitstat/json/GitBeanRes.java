package com.hobby.gitstat.json;

import java.util.List;

import com.hobby.gitstat.domain.GitBean;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "GIT查询返回响应数据")
public class GitBeanRes extends BaseRes {
	
	private List<GitBean> result;

	public List<GitBean> getResult() {
		return result;
	}

	public void setResult(List<GitBean> result) {
		this.result = result;
	}
    
	
	
	
	

}
