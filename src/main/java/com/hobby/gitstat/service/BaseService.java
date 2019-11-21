package com.hobby.gitstat.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hobby.gitstat.config.RedisUtil;
import com.hobby.gitstat.constants.Constants;
import com.hobby.gitstat.domain.GitBean;
import com.hobby.gitstat.tools.JacksonTool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import static com.hobby.gitstat.tools.CommonUtil.toArray;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;
/*
 * @ Redis的操作类
 */
public class BaseService {
	private static final Logger log = LoggerFactory.getLogger(BaseService.class);
	 
	/*
     * @ redis存储邮箱:卡编号 1：N 映射关系, sadd email cardno
     */
    public void saveEmailCardNo(String key,Set<String> cardNos,RedisUtil redisUtil) throws Exception{
    	
    	//避免同一库中的KEY冲突
    	
    	if(cardNos ==null || cardNos.size()==0 ) return;
    	Jedis jedis=redisUtil.getResource();
    	//先清空集合
    	Set<String> members=jedis.smembers(key);
    	
    	if(members != null && members.size() >0) {
    		//log.info("***email:{},取出结果:{}",key,members);
    		//jedis.spop(email, members.size());
    		
    	   Long deleted=jedis.srem(key, toArray(members));
    	  
    	   //log.info("---删除集合数量:{}",deleted.intValue());
    	   
    	   //log.info("---集合数量:{}",jedis.smembers(key));
    	}
    	
    	jedis.sadd(key,toArray(cardNos));//注意：cardNo不重复，使用sadd 集合
    	//log.info("---sadd后集合数量:{}",jedis.smembers(key));
    }

	/*
     * @ smembers key 取出key对应的集合
     */
   public Set<String> getEmailByCardNo(String key,RedisUtil redisUtil) throws Exception{
  	
  	  Jedis jedis=redisUtil.getResource();
   
  	  return jedis.smembers(key);
      
  }
   
   
   
   /*
	 * @ 依据key取出value
	 */
	public String getValue(String key,RedisUtil redisUtil) {
		
		 Jedis jedis=redisUtil.getResource();
		 return jedis.get(key);
	}
   
	
	 /*
     * @key: username:day, value: GitBean
     * 
     * @ expireTime: >0（秒），表示多少秒后过期,0:表示永久存储
     * 
     */
	@Deprecated
    public void saveGitBean(String key, GitBean gitBean,int expireTime,RedisUtil redisUtil) {
        
    	Jedis jedis=redisUtil.getResource();
    	String value=JacksonTool.fromObjectToJson(gitBean);
    	if(expireTime>0) {
    		jedis.setex(key,expireTime, value);
    	}else {
    		jedis.set(key, value);
    	}
    }
	
	 /*
	  * 直接保存提交行数
	  */
	 public void saveGitBean(String key, String commitLines,int expireTime,RedisUtil redisUtil) {
	        
	    	Jedis jedis=redisUtil.getResource();
	    	
	    	if(expireTime>0) {
	    		jedis.setex(key,expireTime,  commitLines);
	    	}else {
	    		jedis.set(key,  commitLines);
	    	}
	    }
   
    
    
	 
	 
    
    /*
     * @ 多次存储需要使用的KEY,统一函数，避免重复写，易出错;更新KEY只需要一个地方维护
     */
    
	public String concatEmailKey(String email) {
		
		return "STAT:"+email;
	}
    
    public String concatCommitKey(String email, String startDate,String endDate) {
    	
    	  return email+":"+startDate+":"+endDate;
    	
    }
    
   
    public String concatCommitByCardNoKey(String email, String startDate,
    		 String endDate,String cardNo) {
    	
  	  return email+":"+startDate+":"+endDate+":"+cardNo;
  	
  }
    
    /*
     * @ email: git eamil account
     * @ day:yyyy-MM-dd
     */
    public String concatDailyRecordsKey (String email, String day) {
    	
    	 return "RECORDS:"+email+":"+day;
    }
    
    
    public void saveDailyRecords(String key, String json,RedisUtil redisUtil) {
    	
    	Jedis jedis=redisUtil.getResource();
    	//N days expired
        jedis.setex(key,Constants.TIME_CACHE_COMMIT_RECORDS, json);
    	
    }
    
   
    public String  getDailyRecords(String key,RedisUtil redisUtil) {
    	
    	Jedis jedis=redisUtil.getResource();
    	
        return jedis.get(key);
    	
    }
     
   
    /*
     *  @ 数据量比较大情况下，导致内存溢出的风险;
     */
    
     public  List<String> getCollectionRecords(String key,RedisUtil redisUtil,int pageNo,int total,int pageSize) {
    	
    	 Jedis jedis=redisUtil.getResource();
    	
    	 if(total == 0L) 
    	 {
    		 return null;
    	 }
    	 if (pageNo <= 0 ) pageNo =1;
    	
    	 
    	 if (total / pageSize == 0)
    	 {
    	     total = total/pageSize;
    	 }else 
    	 {
    	     total = total/pageSize + 1;
    	 }
    	 if (pageNo >= total ) pageNo = total;
    	 
    	 List<String> list = jedis.lrange(key,(pageNo-1)*pageSize, pageNo*pageSize-1);
    
    	 return list;
    	 
     }
     
     /*
      * @ totalSize: 获取集合的总记录长度
      * @ 分页算法，返回总页码
      */
     public int  getPageCount (int totalSize,int pageSize) {
     	
    	 //totalSize : 101 
    	 //共分多少页
    	 int pageCount = totalSize / pageSize; //5
    	 int remain = totalSize % pageSize; // 1
    	 if ( pageCount == 0 ) {
    		 pageCount = 1;
    	 }else { //pageCount > 0 整除
    		 if (remain > 0 ) {
    			 pageCount = pageCount + 1;
    		 }
    	 }
    	 
    	 return pageCount;
 
    	 
     }
    
     
     /*
      * @ 读取集合的全部
      */
     public  List<String> getAllCollectionRecords(String key,RedisUtil redisUtil) {
     
    	 Jedis jedis=redisUtil.getResource();
    	 return jedis.lrange(key,0,-1);
     
     }
  
    /*
     * @ 仅取一次集合尺寸
     */
    public int getCollectionSize(String key,RedisUtil redisUtil) {
    	 
    	 Jedis jedis=redisUtil.getResource();
    	 //return Long.valueOf(jedis.scard(key)).intValue();
    	 return Long.valueOf(jedis.llen(key)).intValue();
     }
}
