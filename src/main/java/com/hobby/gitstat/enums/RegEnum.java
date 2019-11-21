package com.hobby.gitstat.enums;

public enum RegEnum {
	
	CARDNO("#([a-zA-Z0-9]{1,})"),//提取卡号的正则
	
	VUID("VuId:([a-zA-Z0-9]{32})"),
	
	NUMBER("-?[0-9]{1,}");
	
	private String value;
	
	RegEnum(String value) {
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	

}
