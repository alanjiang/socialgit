package com.hobby.gitstat.tools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    
	private  static ThreadLocal<SimpleDateFormat> format=new ThreadLocal<>();
	
	public static SimpleDateFormat getSimpleDateFormat() {
		
		  if( null == format.get()) {
			  format.set(new SimpleDateFormat("yyyy-MM-dd"));
		  }
		  
		  return  format.get();
	}
	
	
    public static String getNowDate() {
		
		LocalDate now = LocalDate.now(ZoneId.of("Asia/Shanghai"));
		
	    return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
	}
	
	public static String addNDays(int n) {
		LocalDate now = LocalDate.now(ZoneId.of("Asia/Shanghai"));
		LocalDate time=now.plusDays(n);
		return  time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		
	}
	
	
	public  static Date toDate(String date) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  sdf.parse(date);
		 
		 
	}
	
   public  static String toYMD(String date) throws Exception{
		
		 return date.substring(0,10);
		 
		 
		 
	}
   
   
   public static void main(String[] args) throws Exception {
	   
	   String time="2019-09-30 12:02:01";
	   
	   System.out.println( toYMD(time));
	   
	   String str="2690502990121";
	   
	   System.out.println( str.substring(1,7));
   }
	
	
}
