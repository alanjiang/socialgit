package com.hobby.gitstat.concurrent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hobby.gitstat.constants.Constants;

public class HttpCaller implements Callable<ResultBean> {
	private CountDownLatch  countDownLatch;
    private String url;
    private String name;
	private String method;
	private String body;
	private RestTemplate restTemplate;
	public HttpCaller(String url, String name,String method,String body,
			RestTemplate restTemplate,CountDownLatch  countDownLatch) {
		super();
		this.url = url;
		this.name=name;
		this.method=method;
		this.body=body;
		this.restTemplate = restTemplate;
		this.countDownLatch =countDownLatch;
	}



	@Override
	public ResultBean call() {
		//PoolingHttpClientConnectionManager m;
		ResultBean rb=new ResultBean();
		try {
			long start=System.currentTimeMillis();
			HttpHeaders requestHeaders = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			requestHeaders.setContentType(type);
			requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
			 
		    requestHeaders.add("agilean-token", Constants.Token_Agilean);
		    
		    HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
		    ResponseEntity<String> res=null;
		    if("GET".equalsIgnoreCase(method)) {
		    	//res=restTemplate.getForEntity(url, String.class);
		    	TimeUnit.MILLISECONDS.sleep(100);
		    	res=restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		    	//System.out.println("--status code="+res.getStatusCode());
		        //System.out.println(res.getBody());
		    }else if("POST".equalsIgnoreCase(method)) {
		    	requestHeaders.add("flag", "json");
		    	
		    	//LogonForm form=JacksonTool.fromJsonToObject(body, LogonForm.class);
		    	requestEntity = new HttpEntity<>(body,requestHeaders);
		    	res=restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		        
		    	//System.out.println("--res="+res);
		    }else {
		    	//System.out.println("--Not support http protocol "+method+" yet");
		    	 throw new Exception("-----Not support Method-----");
		    }
		    if(res!=null)
		    {
		    	rb.setHttpResCode(res.getStatusCodeValue());
		    	if(res.getStatusCodeValue()==200) {
		    		rb.setResult("SUCCESS");
		    	}else {
		    		rb.setResult("FAIL");
		    	}
		    	
		    }else {
		    	rb.setHttpResCode(300);
		    	rb.setResult("ERROR");
		    }
			long end=System.currentTimeMillis();
			long time=(end-start);
			rb.setTime(time);
			rb.setName(name);
			rb.setUrl(url);
			
		}catch(Exception e) {
			 System.out.println("--error happed at:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
			 e.printStackTrace();
			 rb.setResult("ERROR");
		}finally {
			
			//System.out.println("---execute countDwonLatch.countDown() start----");
			this.countDownLatch.countDown();
			//System.out.println("---execute countDwonLatch.countDown() end----");
			//System.out.println(">>>>>"+this.countDownLatch.getCount());
		}
		//System.out.println(">>>>>HttpCaller finished<<<<<");
		
		return rb;
	}

}
