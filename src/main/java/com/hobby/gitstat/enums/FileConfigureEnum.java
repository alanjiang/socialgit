package com.hobby.gitstat.enums;

public enum FileConfigureEnum {

	
	
	EMAIL_COMMIT("email-commit.sh"),
	GIT_PULL_SCRIPT("git-pull.sh"),
	GIT_EMAILS_SCRIPT("emails.sh"),
	GIT_REPOSITORIES("git-repositories.json");
	//GIT_EMAILS("emails.txt");
	private String value;
	FileConfigureEnum(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
}
