package com.hobby.gitstat.beans;

import java.util.Set;

public class EmailBean {

	private Set<String> gitEmails;
    private String gitInstallLocation,gitRepoLocation;
    //Not used
    private String emailCommitScript, emailslScript;
    
    private String scriptFolder;
    
	private String gitPullScript;
	
	// all Reositories for git 
	private Set<GitRepo> gitRepos;


	public Set<String> getGitEmails() {
		return gitEmails;
	}


	public void setGitEmails(Set<String> gitEmails) {
		this.gitEmails = gitEmails;
	}


	public String getGitInstallLocation() {
		return gitInstallLocation;
	}


	public void setGitInstallLocation(String gitInstallLocation) {
		this.gitInstallLocation = gitInstallLocation;
	}


	public String getGitRepoLocation() {
		return gitRepoLocation;
	}


	public void setGitRepoLocation(String gitRepoLocation) {
		this.gitRepoLocation = gitRepoLocation;
	}


	public String getEmailCommitScript() {
		return emailCommitScript;
	}


	public void setEmailCommitScript(String emailCommitScript) {
		this.emailCommitScript = emailCommitScript;
	}


	public String getEmailslScript() {
		return emailslScript;
	}


	public void setEmailslScript(String emailslScript) {
		this.emailslScript = emailslScript;
	}


	public String getScriptFolder() {
		return scriptFolder;
	}


	public void setScriptFolder(String scriptFolder) {
		this.scriptFolder = scriptFolder;
	}


	public String getGitPullScript() {
		return gitPullScript;
	}


	public void setGitPullScript(String gitPullScript) {
		this.gitPullScript = gitPullScript;
	}


	public Set<GitRepo> getGitRepos() {
		return gitRepos;
	}


	public void setGitRepos(Set<GitRepo> gitRepos) {
		this.gitRepos = gitRepos;
	}


	


	
	

	
	
	
	
	
	 
}
