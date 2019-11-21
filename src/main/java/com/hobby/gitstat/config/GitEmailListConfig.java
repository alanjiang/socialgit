package com.hobby.gitstat.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.hobby.gitstat.beans.EmailBean;
import com.hobby.gitstat.beans.GitRepo;
import com.hobby.gitstat.constants.Constants;
import com.hobby.gitstat.enums.FileConfigureEnum;
import com.hobby.gitstat.tools.JacksonTool;
import com.hobby.gitstat.tools.StackTool;
@Configuration
public class GitEmailListConfig implements ResourceLoaderAware {
	private static final Logger log = LoggerFactory.getLogger(GitEmailListConfig.class);
	//GIT配置的路径
	@Value("${git.repository.location}")
	private String gitRepoLocation;
	
	@Value("${git.script.folder}")
	private String scriptFolder;
	private ResourceLoader resourceLoader;
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		 this.resourceLoader=resourceLoader;
		
	}
	public Resource getResource(FileConfigureEnum enu){
		
        return resourceLoader.getResource("classpath:"+enu.getValue());
    }
	
   
	
	@Bean
	public EmailBean emailBean() throws Exception{
		 EmailBean bean=new EmailBean();
		 
		 /*Set<String>  gitEmails=new HashSet<String>();
		 try(BufferedReader reader=new BufferedReader(
		   new InputStreamReader(new FileInputStream(new File(scriptFolder+"/"+FileConfigureEnum.GIT_EMAILS.getValue())))))
		 {
		  String str = null;
		  while((str = reader.readLine()) != null)
		  {
				gitEmails.add(str.trim());
		   }
		 }catch(Exception e) {
			 
			 log.error(StackTool.error(e, 100));
		 }*/
		 String json=readFile(scriptFolder+"/"+FileConfigureEnum.GIT_REPOSITORIES.getValue(),Constants.CHARSET);
		 log.info("----git repositories list JSON:{}",json);
		 List<GitRepo> gitReposList=JacksonTool.fromJsonToList(json,GitRepo.class);
		 Set<GitRepo> gitRepos = new HashSet(gitReposList);
		 log.info("----repos size:{}",gitRepos.size());
		 bean.setGitRepos(gitRepos);//支持多个Git资源库
		 //bean.setGitEmails(gitEmails);
		 bean.setGitRepoLocation(gitRepoLocation.trim());//单一的Git Repository
		 bean.setScriptFolder(scriptFolder.trim());
		 bean.setEmailCommitScript(scriptFolder+"/"+FileConfigureEnum.EMAIL_COMMIT.getValue());
		 bean.setEmailslScript(scriptFolder+"/"+FileConfigureEnum.GIT_EMAILS_SCRIPT.getValue());
		 //git pull
		 bean.setGitPullScript(scriptFolder+"/"+FileConfigureEnum.GIT_PULL_SCRIPT.getValue());
		 //git repos
		
		 return bean;
	}
	
	
	/*
	 *  @ 直接读取配置文件内容
	 */
	private String readFile(String filePath,String encoding) throws Exception {
		System.out.println("====>filePath="+filePath);
		File file=new File(filePath);
		Long len=file.length();
		byte[] fileContent=new byte[len.intValue()];
		try(FileInputStream ins=new FileInputStream(file)){
			ins.read(fileContent);
		}catch(Exception e) {
			log.error(StackTool.error(e,100));
			throw new Exception("文件读取出错");
		}
		return new String(fileContent,encoding);
	}
}
