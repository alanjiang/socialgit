package com.hobby.gitstat.tools;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hobby.gitstat.constants.Constants;
/*
 * 
 * 
 * @ 错误流和正常流分开处理，Closable 实现类的流自动关闭，否则易阻塞、卡死
 */
public class CommandUtil {
	private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);
	public static String execScript(String scriptPath,String[] params ){
	        List<String> cmds=new ArrayList<String>();
		    cmds.add("sh");
			cmds.add(scriptPath);
		    for(String param:params) {
				 cmds.add(param);
			}
	        try
	        {
	               Process p = Runtime.getRuntime().exec(CommonUtil.toArray(cmds));
	              
	               Thread t=new Thread(new ErrorStreamRunnable(p.getErrorStream()));
	               t.start();
	               try(BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
	                 InputStreamReader sReader = new InputStreamReader(bis,Constants.CHARSET);//设置编码方式
	                 BufferedReader bReader=new BufferedReader(sReader))
	               {
	                  p.waitFor(50, TimeUnit.SECONDS);
	                  StringBuilder sb=new StringBuilder();
	                  String line;
	                  while((line=bReader.readLine())!=null)
	                  {
	                   sb.append(line+"\n");
	                  }
	                 return sb.toString();
	              }catch(Exception e) 
	              {
	            	  log.error(StackTool.error(e, 0));
	            	  return Constants.FAIL;
	              }finally {
	            	  
	            	  p.destroy();
	              }
	        }
	        catch(Exception e)
	        {
	        	log.error(StackTool.error(e, 0));
	            return Constants.FAIL;
	        }
	        
      }
}
  
  class ErrorStreamRunnable implements Runnable
  {
    private  InputStream istream;
	private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);
    public ErrorStreamRunnable(InputStream istream)
    {
        this.istream=istream;
    }
    public void run(){
        String line;
        StringBuffer sb=new StringBuffer();
        if(istream == null  ) return;
        try(BufferedReader bReader=new BufferedReader(new InputStreamReader(new BufferedInputStream(istream),Constants.CHARSET))){
        	while((line=bReader.readLine())!=null){
               sb.append(line+"\n");
            }
            if(0<sb.toString().length()) {
            	log.error(sb.toString());
            }
        }catch(Exception ex){
        	log.error(StackTool.error(ex, 0));
        }
    }
}
