package com.hobby.gitstat.tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobby.gitstat.beans.GitRepo;
public class JacksonTool {
		
		private static final Log log = LogFactory.getLog(JacksonTool.class);
		private  final static ObjectMapper mapper = new ObjectMapper(); 
	    static{
	    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    }
		public static <T> T fromJsonToObject(String json,Class<T> clazz){
			try{
				mapper.setSerializationInclusion(Include.NON_NULL);
			    return (T) mapper.readValue(json, clazz); 
			}catch(IOException e)
			{
				e.printStackTrace();	
				return null;
			}
		 }
		
		 public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) 
		 {   
			     return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	     }   
	
	     public static String  fromObjectToJson(Object o){ 
	    	 try{
	    	  mapper.setSerializationInclusion(Include.NON_NULL);  
	    	  return mapper.writeValueAsString(o);  
	    	 }catch(Exception e)
	    	 {
	    		 e.printStackTrace();
	    	 }
	    	 return null;
	     }   
	     
	     
	     public static <T> List<T> fromJsonToList(String json, Class<T> clazz) throws Exception{
	    	 
	    	 JavaType javaType = getCollectionType(ArrayList.class, clazz); 
	         List<T> list =  (List<T>)mapper.readValue(json, javaType); 
	         
	         return list;
	    	 
	     }
	     
	     
	    
	     
	     public static void main(String[] args) throws Exception{
	    	   
	    	 
	    	String json= "[{\"name\":\"valuekanban\", \"folder\":\"/usr/local/git/kanban/valuekanban\"},"+
	    	   
	    	  "{\"name\":\"valuesocializing\", \"folder\":\"/usr/local/git/kanban/valuesocializing\"}]";
	    	 
	    	
	    	 List<GitRepo> list=fromJsonToList(json,GitRepo.class);
	    	 
	    	 System.out.println(list.size());
	    	 
	    	 list.forEach(System.out::println);
	    	 
	     }
}     