/*
 * 文件名：StringLength.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： StringLength.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年11月1日
 * 修改内容：新增
 */
package com.cloud.web;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @Title:  StringLength.java
 * @Package: com.cloud.web.StringLength.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年11月1日 上午9:17:58
 */
public class Main {

	public static void main(String[] args) {
		//计算字符串最后一个单词的长度，单词以空格隔开。 
//		Scanner input = new Scanner(System.in);
//		String string = "";
//		while (input.hasNextLine()) {
//			string = input.nextLine();
//			if (string == null && "".equals(string)) {
//				System.out.println(0);
//			} else {
//				String[] s = string.split(" ");
//				string = s[s.length - 1];
//				System.out.println(string.length());
//			}
//		}
		
//		Scanner inScanner = new Scanner(System.in);
//		String string = inScanner.next().toLowerCase();
//		char string2 = inScanner.next().toLowerCase().charAt(0);
//		int count = 0;
//		if (string != null && string.length() > 0) {
//			for (int i = 0; i < string.length(); i++) {
//				if (string2 == string.charAt(i)) {
//					count++;
//				}
//			}
//		}
//		System.out.println(count);
		
//		Scanner inScanner = new Scanner(System.in);
//		while (inScanner.hasNext()) {
//			int num = inScanner.nextInt();
//			TreeSet<Integer> set = new TreeSet<Integer>();
//			for (int i = 0; i < num; i++) {
//				int m = inScanner.nextInt();
//				set.add(m);
//			}
//			
//			for (Integer integer : set) {
//				System.out.println(integer);
//			}
//		}
		
		Scanner inScanner = new Scanner(System.in);
		while (inScanner.hasNext()) {
			String string = (String) inScanner.next();
			if (string != "" && string.length() > 0) {
				while (string.length() > 8) {
					String string1 = string.substring(0,8);
					System.out.println(string1);
					string = string.substring(8);
				}
				
				if (string.length() < 8) {
					String s = addRightZero(string, 8);
					System.out.println(s);
				} else if (string.length() == 8) {
					System.out.println(string);
				} 
			}
		}
	}
	
	public static String addRightZero(String string, int num) {
		while (string.length() < num) {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(string).append("0");
			string = stringBuffer.toString();
		}
		return string;
	}
	
}
