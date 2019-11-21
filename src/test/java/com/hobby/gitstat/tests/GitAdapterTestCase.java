package com.hobby.gitstat.tests;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.hobby.gitstat.Application;
import com.hobby.gitstat.config.RedisUtil;
import com.hobby.gitstat.domain.GitBean;
import com.hobby.gitstat.service.GitComputerService;
import com.hobby.gitstat.tools.JacksonTool;

@RunWith(SpringRunner.class)
@Configuration
@ComponentScan(value= {"cn.agilean.gitadapter"})
@SpringBootTest(classes= {Application.class})
public class GitAdapterTestCase {
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private GitComputerService gitComputerService;
	
	@Ignore
	@Test
	public void testSaveSbDailyRecord() {
		
		String email="jiangpeng@agilean1.cn";
		String day="2019-09-30";
	    
		String json="[{\"workLoadType\":\"fcb50b3fbb384dd399e52cf4769a91ce\",\"personId\":\"chenzr@agilean.cn\",\"date\":1567353599000,\"workLoad\":20,\"cardId\":\"4810\",\"orgId\":\"771ac1a5-fca5-4af2-b744-27b16e989b18\"},{\"workLoadType\":\"fcb50b3fbb384dd399e52cf4769a91ce\",\"personId\":\"chenzr@agilean.cn\",\"date\":1567353599000,\"workLoad\":0,\"cardId\":\"0\",\"orgId\":\"771ac1a5-fca5-4af2-b744-27b16e989b18\"}]";

		
		gitComputerService.saveSbDailyRecords(email, day, json);
		
	}
	
	@Ignore
	@Test
	public void getSbDailyRecords() throws Exception{
		
		String email="jiangpeng@agilean1.cn";
		String day="2019-09-30";
		
		String json=gitComputerService.getSbDailyRecords(email, day);
		
		System.out.println("--JSON:"+json);
		
		List<GitBean> list=JacksonTool.fromJsonToList(json, GitBean.class);
		
		list.forEach(System.out::println);
		
		
		
	}
	
	
	
}
