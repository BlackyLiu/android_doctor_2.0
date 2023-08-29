package com.base.util;

import java.util.regex.Pattern;

public class Strings {

	
	public static boolean isEmpty(String str) {
		return str==null?true:"".equals(str.trim());
	}
	
	public static String f2U(String str) {
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	public static String f2L(String str) {
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
	/**
	 * $index 意味着 你匹配到的是 group的第几组数
	 * 第一个字母 转小写
	 * 最好的办法 是看看 replaceAll 里面有没有 在替换内容里面写代码的 
	 * @param str
	 * @return
	 */
	public static String hump2Breaking(String str) {
		return f2L(str).replaceAll("[A-Z]+", "_$0").toLowerCase();
	}
	
	public static boolean isNumber(String str) { 
        Pattern pattern = Pattern.compile("(^[\\-0-9][0-9]*(.[0-9]+)?)$"); 
        return pattern.matcher(str).matches(); 
   }
	
	public static void main(String[] args) {
		
//		System.out.println(hump2Breaking("GtuMMSsS"));
		
        String s1 = "00.1";
        System.out.println(Double.valueOf(s1));
		boolean n2 = isNumber(s1);
		System.out.println(n2);
		
	}
	
	
}
