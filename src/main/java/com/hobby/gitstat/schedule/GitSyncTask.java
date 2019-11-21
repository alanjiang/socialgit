package com.hobby.gitstat.schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hobby.gitstat.service.GitComputerService;
import com.hobby.gitstat.service.GitMultiAdapterService;
import com.hobby.gitstat.tools.StackTool;
import com.hobby.gitstat.tools.TimeUtil;

/*
 *  @ 作者 ： 姜鹏   2019/08/02 ,2019/11/12 
 *  @ git无限资源库统计代码行解决方案
 *  @ 函数行统计爬虫分析
 * 
 */
@Component("gitSyncTask")
public class GitSyncTask {
	
	 private static final Logger log = LoggerFactory.getLogger(GitSyncTask.class);
	 @Autowired
	 private GitMultiAdapterService gitMultiAdapterService;//支持多个GIT资源库的配置
	 @Autowired
	 private GitComputerService gitComputerService;
	 //"0 15 10 ? * *" 每天上午10:15触发
	 //@Scheduled(cron = "0 15 2 * * ?")//每天2：15执行一次 
	 @Scheduled(cron = "0 1 6 * * ?")//每天2：15执行一次 
	//@Scheduled(cron = "0/30 * * * * *")//每30秒执行一次
	 //@Scheduled(cron = "0 0/3 * * * *")//每3分钟执行一次
	//@Scheduled(cron = "0 0 */4 * * ?")//每4小时执行一次
     public void syncDatas() {
        // task execution logic
		try {
			log.info("--- *******************----");
		    log.info("--- *                 *----");
		    log.info("--- *                 *----");
		    log.info("--- * 开始整个git数据统计*----");
		    log.info("--- *                 *----");
		    log.info("--- *                 *----");
		    log.info("--- *******************----");
		    String yesterday=TimeUtil.addNDays(-1);
		    String yesterdayStart=yesterday+" 00:00:00";
		    String yesterdayEnd=yesterday+" 23:59:59";
		    gitMultiAdapterService.updateAllGitBranches();
		    log.info("---开始统计Yesterday--");
		    gitMultiAdapterService.initEmails(yesterdayStart, yesterdayEnd);
		    gitMultiAdapterService.statisGitLog(yesterdayStart, yesterdayEnd,"--all") ;
		    gitComputerService.computeStatDatas(yesterdayStart, yesterdayEnd);
		    log.info("---结束统计Yesterday--");  
		  
		}catch(Exception e) {
			log.error(StackTool.error(e, 0));
		}
		
    }
}
