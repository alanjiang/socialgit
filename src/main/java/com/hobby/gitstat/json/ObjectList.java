package com.hobby.gitstat.json;

import java.util.List;

public class ObjectList<T> {

	
 private List<T> objects;
	 
	 private int pageNo;
	 
	 private int totalPage;
	 
	 private int pageSize;
	 
	 private int total;

	public List<T> getObjects() {
		return objects;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	 

	
     
	  
	
}
