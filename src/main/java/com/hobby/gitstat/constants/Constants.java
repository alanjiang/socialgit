package com.hobby.gitstat.constants;

public class Constants {

	 public final static int TIME_CACHE_COMMIT_RECORDS=2592000;//30天
	 public final static int PAGE_SIZE=20;
	 
	 public final static String SUCCESS="OK";
	 
	 public final static String FAIL="FAIL";
	 
	 public static String CHARSET="utf8";
	 
	 //无卡提交标识
	 public final static String UNKONW_CARD_ID="0";
	 
	 public final static String Token_Agilean="8648E7928F01F291189241C73F2B0904";
		
	 public final static String FUNCTION_STAT_SERVICE_LIST="/api/v1/value-units/filter";
	 
	 //2,文件同步:
	 public final static String FILE_STAT_API = "/api/v1/sync/class";
	 
	//3,函数同步:
	public final static String  FUNCTION_STAT_API = "/api/v1/sync/func";
	 
	 //caced key
	 public final static String FILE_NAME_KEY = "STAT:fileName"; //所有类的键值
	 public final static String FUNCTION_NAME_KEY ="STAT:function";//所有函数的键值
	 
	 // max size for cached record cursor 
	 public final static int MAX_CACHED_RECORDS_SIZE = 20;
	 
}
