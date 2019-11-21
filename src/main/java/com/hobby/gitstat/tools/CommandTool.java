package com.hobby.gitstat.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hobby.gitstat.constants.Constants;
public class CommandTool {
 
	private static final Logger log = LoggerFactory.getLogger(CommandTool.class);
	
	/*
	 * @非用脚本，调用简单命令行的方式
	 */
	public static String cmd(String command) throws InterruptedException {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
            return Constants.FAIL;
        }
        try {
            pro = runTime.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
        	log.error(StackTool.error(ex, 100));
        	return Constants.FAIL;
        }
        return returnString;
    }
	
	
	

	
public static String execScript2(String scriptPath,String[] params ) {
		
		try{
			StringBuffer sb=new StringBuffer();
			sb.append("sh "+scriptPath);
			if(params != null && params.length >0) {
			  for(String param: params) {
				 sb.append(" ").append(param);
			  }
			}
           log.info("--cmd="+sb.toString());
           
            return exeCommand(sb.toString());
        }
        catch (Exception ex)
        {
            log.error(StackTool.error(ex, 100));
            return Constants.FAIL;
        }
	}
	
	
	
	/***
	 * @ 调用脚本sh /XXX.sh 的执行方式 
	 */
	
   public static String execScript(String scriptPath,String[] params ) {
	  
	try{
		  
		 List<String> cmds=new ArrayList<String>();
		 cmds.add("chmod +x scriptPath");
		 cmds.add(scriptPath);
		 for(String param:params) {
			 cmds.add(param);
		 }
			
        Runtime runtime = Runtime.getRuntime();
        Process pro = runtime.exec(CommonUtil.toArray(cmds));
        pro.waitFor(50, TimeUnit.SECONDS);
        //log.info("---status="+status);
        StringBuffer sb = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()))){
            String line;            
            while ((line = br.readLine())!= null)
            {                
               sb.append(line).append("\n");
               //log.info("stdout返回的行记录:"+line);
            }
        }
        
        return sb.toString();
    }
    catch (Exception ex)
    {
    	System.out.println(StackTool.error(ex, 100));
        log.error(StackTool.error(ex, 100));
        return Constants.FAIL;
    }
}

	/**
     * 执行指定命令
     * 
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    private static  String exeCommand(String command) throws IOException {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            int exitCode = exeCommand(command, out);
            if (exitCode == 0) {
            	 return out.toString(Constants.CHARSET);
            } else {
                System.out.println("命令运行失败！");
                return Constants.FAIL;
            }
           
        }
    }

    /**
     * 执行指定命令，输出结果到指定输出流中
     * 
     * @param command 命令
     * @param out 执行结果输出流
     * @return 执行结果状态码：执行成功返回0
     * @throws ExecuteException 失败时抛出异常，由调用者捕获处理
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    private static  int exeCommand(String command, OutputStream out) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler pumpStreamHandler = null;
        if (null == out) {
            pumpStreamHandler = new PumpStreamHandler();
        } else {
            pumpStreamHandler = new PumpStreamHandler(out);
        }

        // 设置超时时间为20秒
        ExecuteWatchdog watchdog = new ExecuteWatchdog(20000);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        executor.setWatchdog(watchdog);

        return executor.execute(commandLine);
    }

    
	
	

}
