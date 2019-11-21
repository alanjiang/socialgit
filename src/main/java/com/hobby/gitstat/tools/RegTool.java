package com.hobby.gitstat.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hobby.gitstat.enums.RegEnum;

/*
 * @正则工具类
 */
public class RegTool {

	/*
	 * @ 找到第一个匹配即可
	 * @ RegEnum: 所有的正则表达式
	 */
	public static String match(final String line,RegEnum reg) {
		Pattern p = Pattern.compile(reg.getValue());  
	    Matcher m = p.matcher(line);
	    while(m.find()) { //如果有多组匹配，仅取第一组
	       return m.group(1);
	    }  
	    return null;
	}
	
	//获取多个卡号 ,不可重复
	public static Set<String> getCardNos(List<String> lines) throws Exception{
		
		Set<String> cardNos=new HashSet<>();
		if(lines == null || lines.size() == 0)  return cardNos;
	    for(String line: lines) {
	    	String cardNo=match(line,RegEnum.CARDNO);
	    	if ( null != cardNo ) {
	    		cardNos.add(cardNo);
	    	}
	    }
	    return cardNos;
	}
	
	public static boolean isDigital(String str) {
		
		Pattern p = Pattern.compile(RegEnum.NUMBER.getValue());  
	    Matcher m = p.matcher(str);
	    return m.matches();
		
	}
	
	public static void main(String[] args) throws Exception{
		
		System.out.println(isDigital("-5038"));
		Integer.parseInt("-123");
	}

}
