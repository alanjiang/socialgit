package com.hobby.gitstat.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hobby.gitstat.tools.StackTool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/*
 *  API 覆盖所有的Redis操作
 *  官方文档
 *  http://redisdoc.com/hash/hset.html
 */
@Component
public class RedisUtil{
	private static final Logger log=LoggerFactory.getLogger(RedisUtil.class);
	private static final int REDIS_EXPRIE_TIME =3600*24;
	@Autowired
	private JedisPool jedisPool;
	@Value("${redis.password}")
	private String pwd;
	@Value("${redis.authen}")
	private boolean authen;
	public Jedis getResource(){
		//closable的实现，无需要手动维护关闭
        try (Jedis jedis=jedisPool.getResource()){
           if(authen) {
        	   jedis.auth(pwd);
           }
          
            return jedis;
        } catch (Exception e) {
           log.error(StackTool.error(e, 60));
        }
           return null;
        
    }
	
	
	
    
    


}
