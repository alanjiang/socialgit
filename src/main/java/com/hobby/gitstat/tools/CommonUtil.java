package com.hobby.gitstat.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hobby.gitstat.domain.GitBean;
public class CommonUtil {

	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	
	public static String  readFile(String file) throws Exception {
		  StringBuffer sb = new StringBuffer();
		  try(InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); // 建立一个输入流对象reader
			  BufferedReader br = new BufferedReader(reader)  )
		  {	
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
		        
		    }
			   
		  }catch(Exception e) {
			  StackTool.error(e, 100);
		  }
		  
		  return sb.toString();
			 
		}
	
	
	
	/*
	 * @ 读取从shell stdout输出的行，公用函数 
	 * @ charset utf8
	 */
	
	public static List<String>  readLines(final String stdout,String charset) throws Exception {
	  List<String> list=new ArrayList<String>();
	  try( BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(stdout.getBytes(Charset.forName(charset))), Charset.forName(charset))))
	  {	
		
		  br.lines().forEach(t->{
			  if(!t.equals(""))
			  list.add(t.trim());
		  });
		   
	  }catch(Exception e) {
		     log.error(StackTool.error(e, 100));
			 throw new Exception("读取stdout异常");
	   }
	   return list;
		 
	}
	
	public static Set<String>  readSetLines(final String stdout,String charset) throws Exception {
		  Set<String> set=new HashSet<String>();
		  try( BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(stdout.getBytes(Charset.forName(charset))), Charset.forName(charset))))
		  {	
			
			  br.lines().forEach(t->{
				  if(!t.equals(""))
					  set.add(t.trim());
			  });
			   
		  }catch(Exception e) {
			     log.error(StackTool.error(e, 100));
				 throw new Exception("读取stdout异常");
		   }
		   return set;
			 
		}
	
	public static <T> List<T> toReadOnlyList (T[] arrays){
		
		return Arrays.asList(arrays);
		
	}
	
   public static <T> List<T> toList (T[] arrays){
		
		return Stream.of(arrays).collect(Collectors.toList());
		
	}
   public static <Strig> String[] toArray(Collection<String> list) throws Exception{
	       
	        /*
	        list.stream().toArray(new IntFunction<String[]>() {
				@Override
				public String[] apply(int value) {
					return new String[value];
				}
	        });*/
	  
	     return (String[])list.stream().toArray(String[]::new);
	   
   }
   
   

   
   
   /*@ 将用户的提交代码行数结果进行处理：
   @ shell stdout: added:222354,removed:111658,total:110696
 */
   @Deprecated
  public static GitBean parseCommitLinesResult(final String result) {
 	
 	GitBean gb=new GitBean();
 	String[] results=result.split("\\,");
 	
 	/*String added=results[0];
 	String removed=results[1];*/
 	String total=results[2];
 	/*
 	String[] addedArray=added.split("\\:");
 	String[] removedArray=removed.split("\\:");
 	*/
 	String[] totalArray=total.split("\\:");
 	
 	/*if(addedArray.length == 1) {
 		gb.setAdded(0);
 	}else {
 		if("".equals(addedArray[1]) || null == addedArray[1]) {
 			gb.setAdded(0);
 		}else {
 			gb.setAdded(Integer.valueOf(addedArray[1]));
 		}
 	}
 	
 	if(removedArray.length == 1) {
 		gb.setRemoved(0);
 	}else {
 		if("".equals(removedArray[1]) || null == removedArray[1]) {
 			gb.setRemoved(0);
 		}else {
 			gb.setRemoved(Integer.valueOf(removedArray[1]));
 		}
 		
 	}*/
 	
 	
 	if(totalArray.length == 1) {
 		gb.setWorkLoad(0);
 	}else {
 		
 		if(null == totalArray[1] || "".equals(totalArray[1])) {
 			gb.setWorkLoad(0);
 			
 		}else {
 			if(RegTool.isDigital(totalArray[1])) {
 			  gb.setWorkLoad(Integer.valueOf(totalArray[1]));
 			}else {
 				gb.setWorkLoad(0);
 			}
 		}
 		
 		
 	}
 	
 	return gb;
 	  
 }
 
  /*
   *  @ 直接得到净增行
   */
  public static String parseCommitLines(final String result) {
	 	
	 	
	 	String[] results=result.split("\\,");
	 	String total=results[2];
	 	String[] totalArray=total.split("\\:");
	 	
	 	if(totalArray.length == 1) {
	 		return "0";
	 	}else {
	 		if(null == totalArray[1] || "".equals(totalArray[1])) {
	 			return "0";
	 		}else {
	 			if(RegTool.isDigital(totalArray[1].trim())) {
	 			  return totalArray[1].trim();
	 			}else {
	 				return "0";
	 			}
	 		}
	 	}  
	 }

}
