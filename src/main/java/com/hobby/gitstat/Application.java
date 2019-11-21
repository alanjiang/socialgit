package com.hobby.gitstat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@ComponentScan(value = {"com.hobby.gitstat"})
//@PropertySource(value = "classpath:application.properties")
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class Application {
    
	
	public static void main(String[] args)  throws Exception {
		
		ConfigurableApplicationContext ctx=SpringApplication.run(Application.class, args);
		/*GitComputerService gitComputerService=ctx.getBean(GitComputerService.class);
		gitComputerService.cleanDatas();*/
		/*
		RestTemplate restTemplate=ctx.getBean(RestTemplate.class);
		String url="http://localhost:8923/gitstat";
		ReqBean rb=new ReqBean();
		rb.setEmail("jiangpeng@agilean.cn"); 
		rb.setTime("2019-09-02");
		ResponseEntity<String> result = restTemplate.postForEntity(url, rb, String.class);
	    */
	  }	

	
}
