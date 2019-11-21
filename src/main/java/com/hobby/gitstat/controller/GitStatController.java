package com.hobby.gitstat.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hobby.gitstat.domain.GitBean;
import com.hobby.gitstat.json.GitBeanRes;
import com.hobby.gitstat.json.ReqBean;
import com.hobby.gitstat.service.GitComputerService;
import com.hobby.gitstat.tools.JacksonTool;


@Controller
public class GitStatController {

	private static String ERROR_CODE="10009";
	@Autowired
	private GitComputerService gitComputerService;
	
	private static final Logger log = LoggerFactory.getLogger(GitStatController.class);
	 
	 /*
	  * @ 提供外部查询服务
	  * 
	  * @ time:yyyy-MM-dd
	  * @ emai: git account
	  * @ 返回结果集（当天多个关联卡的提交记录)
	  */
	@RequestMapping(value= { "/gitstat"},method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody  GitBeanRes querySbDailyCommit(@RequestBody ReqBean body) {
		
		log.info("==> body:{}",body.toString());
		GitBeanRes res=new GitBeanRes();
		res.setResCode("0");
		res.setResMsg("成功");
		if(null == body) {
			res.setResCode(ERROR_CODE);
			res.setResMsg("非法请求");
			return res;
		}
		
		if(body.getTime() == null || "".equals(body.getTime()) ) {
			res.setResCode(ERROR_CODE);
			res.setResMsg("时间参数缺少");
			return res;
		}
		
		if(body.getEmail() == null || "".equals(body.getEmail()) ) {
			res.setResCode(ERROR_CODE);
			res.setResMsg("用户git邮箱帐号参数缺少");
			return res;
		}
		Pattern p=Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m=p.matcher(body.getTime());
		if(!m.matches()) {
			res.setResCode(ERROR_CODE);
			res.setResMsg("日期格式不正确");
			return res;
		}
		try {
		  String json =gitComputerService.getSbDailyRecords(body.getEmail().trim(), body.getTime());
		  log.info("==>查询结果:{}",json);
		  if(json == null || "".equals(json)) {
			  res.setResMsg("未查询到结果");
			  return res;
		  }
		  
		  List<GitBean> list=JacksonTool.fromJsonToList(json, GitBean.class);
		  res.setResult(list);
		  return res;
		
		}catch(Exception e) {
			res.setResCode(ERROR_CODE);
			res.setResMsg("服务异常");
			return res;
		}
	
	}
	
}
