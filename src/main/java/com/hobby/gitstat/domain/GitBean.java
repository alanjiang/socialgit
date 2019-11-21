package com.hobby.gitstat.domain;

import java.util.Date;

public class GitBean {
	
	private String workLoadType="testId";
	//personId:邮箱
	private String personId,
	startDate,endDate;
	private Date date;
	//private int removed, added; 
	
	private int workLoad;//删除代码行、新增代码行、净提交代码行
	private String cardId;// 卡编号
	
	private String  orgId;
	
	
	public String getWorkLoadType() {
		return workLoadType;
	}
	public void setWorkLoadType(String workLoadType) {
		this.workLoadType = workLoadType;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	public int getWorkLoad() {
		return workLoad;
	}
	public void setWorkLoad(int workLoad) {
		this.workLoad = workLoad;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Override
	public String toString() {
		return "GitBean [workLoadType=" + workLoadType + ", personId=" + personId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", date=" + date + ", workLoad=" + workLoad + ", cardId=" + cardId
				+ ", orgId=" + orgId + "]";
	}
	
	
	
	
	/*  
	 * [
	 *  {"workLoad": 2000, "workLoadType":"testId",
	 *   "date": DateTime,
	 *   "satart_date":"2019-05-01",
	 *   "end_date":"2019-07-29",
	 *   "personId":"jiangpeng@agilean.cn",
	 *   "cardId": "234"
	 *  },...
	 *  
	 *  {
	 *   ...
	 *  }
	 * ]
	 *  
	 *  
	 */
	
	
}
