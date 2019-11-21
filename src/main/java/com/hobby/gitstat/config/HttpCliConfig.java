package com.hobby.gitstat.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/*
 * spring-boot http client configuration
 */

@Configuration
public class HttpCliConfig {
	
	/**HTTP Pool */
	@Value("${http.connection.timeout}")
    private Integer httpConnTimeOut;
	@Value("${http.socket.timeout}")
    private Integer httpSocketTimeOut;
	@Value("${http.requst.timeout}")
    private Integer httpReqTimeOut;
	@Value("${http.pool.connection.max}")
    private Integer httpPoolConnMax;
	@Value("${http.route.per.max}")
    private Integer httpRoutePerMax;
	@Value("${http.keep.alive.time}")
    private Integer httpKeepAliveTime;
	@Value("${http.error.retry.times}")
	private Integer httpErrorRetryTimes;
	 /****start Http Client *****/ 
	  @Bean
	   PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		    PoolingHttpClientConnectionManager poolHttpManager=new PoolingHttpClientConnectionManager();
			poolHttpManager.setMaxTotal(httpPoolConnMax);
			poolHttpManager.setDefaultMaxPerRoute(httpRoutePerMax);
			return poolHttpManager;
	   }
	   
	   @Bean 
	   ConnectionKeepAliveStrategy keepAliveStrategy() {
		    
		    	return (HttpResponse response, HttpContext context)->{
		               HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
		           while (it.hasNext()) {
		                HeaderElement he = it.nextElement();
		                String param = he.getName();
		                String value = he.getValue();
		                if (value != null && param.equalsIgnoreCase("timeout")){
		                    return Long.parseLong(value) * 1000;
		                }
		            }
		                return httpKeepAliveTime;
		    	  };   
	   }
	   
	   @Bean
	   RequestConfig requestConfig() {
		   return RequestConfig.custom()  
		            .setConnectionRequestTimeout(httpReqTimeOut )  
		            .setConnectTimeout(httpConnTimeOut)  
		            .setSocketTimeout(httpSocketTimeOut)
		            .build();  
	   }
	    
	  //支持HTTP、HTTPS
	   @Bean
	   public CloseableHttpClient httpClient(RequestConfig requestConfig,PoolingHttpClientConnectionManager connectionManager){
	       Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
	           .register("http", PlainConnectionSocketFactory.getSocketFactory())
	           .register("https", SSLConnectionSocketFactory.getSocketFactory())
	           .build();
	       return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).setRetryHandler(new DefaultHttpRequestRetryHandler(httpErrorRetryTimes, false)).build();
	   }

	   @Bean
	   HttpComponentsClientHttpRequestFactory clientHttpRequestFactory ( CloseableHttpClient httpClient) {
	       return new HttpComponentsClientHttpRequestFactory(httpClient);
	   }
	   @Bean
	   public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory clientHttpRequestFactory) {
	       RestTemplate restTemplate = new RestTemplate();
	       restTemplate.setRequestFactory(clientHttpRequestFactory);
	       restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
	       return restTemplate;
	   }
	   /****end Http Client *****/  

}
