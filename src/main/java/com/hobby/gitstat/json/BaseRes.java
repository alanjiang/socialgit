package com.hobby.gitstat.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "标准返回响应数据")
public class BaseRes {

	public final static String RES_SUCCESS="0";
	
	public final static String RES_FAIL="1009";
	
	@ApiModelProperty(value = "返回码")
	private String resCode;
	@ApiModelProperty(value = "返回消息")
	private String resMsg;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	 
	 
	 
}
