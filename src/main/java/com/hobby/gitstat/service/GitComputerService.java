package com.hobby.gitstat.service;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hobby.gitstat.beans.EmailBean;
import com.hobby.gitstat.config.RedisUtil;
import com.hobby.gitstat.json.GitReqBean;
import com.hobby.gitstat.tools.JacksonTool;

@Service("gitComputerService")
public class GitComputerService  extends BaseService {
  
	private static transient Logger log = LoggerFactory.getLogger(GitComputerService.class);
	@Autowired
	private EmailBean emailBean;//邮箱列表启动时已收集
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 *  @按人、天、来批量存数据
	 */
	public void computeStatDatas(String startDate,String endDate) throws Exception
	{
		 Set<String> emails=emailBean.getGitEmails();
		 /***** 开始遍历用户 ****/
		 for(String email: emails) 
		 {
			 /***** 某人、某日总提交行数开始 ****/
			 String commitLines=getValue(concatCommitKey(email, startDate,endDate), redisUtil);
			 if(commitLines == null ) {
				 log.warn("---用户:{} ,总行数数据都没有，没有统计意义",email);
				 continue;
			 }
			 int totalLines=Integer.valueOf(commitLines);
			 if(totalLines == 0 ) {
				 log.warn("---用户:{} ,总行数数据都没有，没有统计意义",email);
				 continue;
			 }
			 GitReqBean bean = new GitReqBean();
			 bean.setBranch("all");//所有的分支
			 bean.setLines(totalLines);
			 bean.setTime(startDate);
			 bean.setUser(email);
			 String json = JacksonTool.fromObjectToJson(bean);
			 saveSbDailyRecords(email,startDate,json);
		 }
	/***** 结束遍历用户 ****/   
		 
 
	}
	
	
	 /*
	  * @ 保存用户day的提交数据
	  */
	 public void saveSbDailyRecords(String email,String day, String json) {
		 String key=concatDailyRecordsKey(email, day);
		 saveDailyRecords(key, json, redisUtil);
	 }
	
	 public String getSbDailyRecords(String email,String day) {
		 String key=concatDailyRecordsKey(email, day);
		 return getDailyRecords(key, redisUtil);
	 }
	

	 
	
}
