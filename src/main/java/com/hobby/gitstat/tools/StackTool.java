package com.hobby.gitstat.tools;

public class StackTool {
	
	 /* 将异常信息按照指定行数输出
	  * @ t: 异常对象
	  * @ len: 0, 表示全部输出，>0 表示输出的最大行数
	  */
	 public static <T extends Exception> String error(T t, int len) {
		     StringBuffer sb=new StringBuffer();
		    
		     Exception ex=(Exception)t;
		     sb.append(ex).append("\n\t");
		     if(len == 0) {
		    	 for(int i=0;i<ex.getStackTrace().length;i++) {
			    	 
			    	 StackTraceElement el=ex.getStackTrace()[i];
			    	 sb.append(el.getClassName()).append(" ").append(el.getMethodName()).
		             append(el.getLineNumber()).append(" \n\t");
			     }
		     }else {
		    	 for(int i=0;i<ex.getStackTrace().length;i++) {
			    	 if(i<=len) {
			    	  StackTraceElement el=ex.getStackTrace()[i];
			    	  sb.append(el.getClassName()).append(", ").append(el.getMethodName()).
		              append(",").append(el.getLineNumber()).append(" \n\t");
			    	 }
			     }
		    	 
		     }
		     
		     return sb.toString();
		     
	 }

}
