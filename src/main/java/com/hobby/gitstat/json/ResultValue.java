package com.hobby.gitstat.json;

import java.util.List;

public class ResultValue {
  private String id,name,orgId,containerId,vutId;
  List<IdNameValue> customFields;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getOrgId() {
	return orgId;
}
public void setOrgId(String orgId) {
	this.orgId = orgId;
}
public String getContainerId() {
	return containerId;
}
public void setContainerId(String containerId) {
	this.containerId = containerId;
}
public String getVutId() {
	return vutId;
}
public void setVutId(String vutId) {
	this.vutId = vutId;
}
public List<IdNameValue> getCustomFields() {
	return customFields;
}
public void setCustomFields(List<IdNameValue> customFields) {
	this.customFields = customFields;
}
  	 
	 
}
