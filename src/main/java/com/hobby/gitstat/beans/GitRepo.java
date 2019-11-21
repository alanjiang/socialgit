package com.hobby.gitstat.beans;

public class GitRepo {

	  @Override
	public int hashCode() {
		return this.getFolder().length();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * folder属性一样就视为同一个对象，Set中不会重复
	 */
	@Override
	public boolean equals(Object obj) {
		
         if(! (obj instanceof GitRepo)) return false;
         
         GitRepo gitRepo =(GitRepo)obj;
         
         return gitRepo.getFolder() .equals(this.getFolder());
         
		
	}

	private String name,folder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "GitRepo [name=" + name + ", folder=" + folder + "]";
	}
	  
	
	  
	  
}
