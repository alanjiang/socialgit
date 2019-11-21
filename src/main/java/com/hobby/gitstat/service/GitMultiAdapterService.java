package com.hobby.gitstat.service;
import static com.hobby.gitstat.tools.CommandUtil.execScript;
import static com.hobby.gitstat.tools.CommonUtil.parseCommitLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hobby.gitstat.beans.EmailBean;
import com.hobby.gitstat.beans.GitRepo;
import com.hobby.gitstat.config.RedisUtil;
import com.hobby.gitstat.constants.Constants;
import com.hobby.gitstat.tools.CommonUtil;
import com.hobby.gitstat.tools.RegTool;


/*   在相同的时间段内,同一个用户：
 * 
 *   TotalCommit:       总提交行数：
 *   
 *   CardCommit:        各个卡上提交的行数：
 *   
 *   UnknownCardCommit: 未知卡上的提交行数：
 * 
 *   @ 作者：姜鹏, 2019/08/02 05:20:12 长沙银行项目
 *   
 *   支持无限个GIT分支求和 
 * 
 */
@Service("gitMultiAdapterService")
public class GitMultiAdapterService extends BaseService {
	private static final Logger log = LoggerFactory.getLogger(GitMultiAdapterService.class);
	@Autowired
	private EmailBean emailBean;//邮箱列表启动时已收集
	@Autowired
    private RedisUtil redisUtil;
	
	
	public void initEmails(String startDate,String endDate) throws Exception {
		
		Set<String> emails=new HashSet<String>();
		for(GitRepo repo:emailBean.getGitRepos()) {
			 String stdout=execScript(emailBean.getEmailslScript(),
				new String[] {repo.getFolder(),
						 startDate,
					     endDate
						      
			 });
			 log.info("---stdout:{}",stdout);
			 Set<String> lines=CommonUtil.readSetLines(stdout, "utf8");
			 log.info("---lines:{}",lines.toString());
			 if(lines != null && lines.size()>0) {
				 emails.addAll(lines);
			 }
			
		}
		emailBean.setGitEmails(emails);
		log.info(" emails:{}",emails.toString());
	}
	
	public void updateAllGitBranches() throws Exception {
		
		for(GitRepo repo:emailBean.getGitRepos()) {
			
			log.info("---start git pull --all for repo: {}",repo.getFolder(),
	    			execScript(emailBean.getGitPullScript(),
					new String[] {repo.getFolder()}));
		}
    	
	}
   
    
    
    public void statisGitLog(String startDate,String endDate,String branch) throws Exception{
         String stdout=null;
         String key=null;
         String commitLines=null;
    	 for(String email: emailBean.getGitEmails()){
    		int count=0;//按邮箱统计用户、天所有提交的所有行
    		//log.info("---开始同步用户{}:总提交数据",email);
    		//#参数$1:git的文件夹位置,$2:beginTime, $3:endTime, $4:邮箱,$5:分支 
    		for(GitRepo repo:emailBean.getGitRepos()) {
    		stdout=execScript(emailBean.getEmailCommitScript(),
    				new String[] {repo.getFolder(),
    						startDate,
   					        endDate,
    						email,
    						branch
    						      
    		  });
    		
    		//log.info("--所有提交stdout结果:{}---"+stdout);
    		if(!Constants.FAIL.equals(stdout)) { //执行成功返回结果
    			//key格式： email:yyyy-MM-dd:yyyy-MM-dd:name(git repo)
    			commitLines=parseCommitLines(stdout.replaceAll("\r|\n", ""));//去掉换行符
    			count+=Integer.valueOf(commitLines);
    		}else {
    			log.warn("--同步用户{}:总提交数据出错",email);
    		}
    		
    	  }//end of GIT REPO
    		log.info("--结束同步用户{}:总提交代码总行数：{}行---",email,count);
    		key=concatCommitKey(email, startDate,endDate);
    		saveGitBean(key,String.valueOf(count),0,redisUtil);	
    		
    	}//end of email
    	
    	
    }
    
   
 
   
    
    
     
    
  
 
	
}
