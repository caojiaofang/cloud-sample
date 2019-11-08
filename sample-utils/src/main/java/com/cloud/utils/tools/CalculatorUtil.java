/*
 * 文件名：CalculatorUtil.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CalculatorUtil.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年11月7日
 * 修改内容：新增
 */
package com.cloud.utils.tools;

import java.util.Scanner;
import java.util.Stack;

/**
 * @Title:  CalculatorUtil.java
 * @Package: com.cloud.utils.tools.CalculatorUtil.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年11月7日 下午5:50:12
 */
public class CalculatorUtil {

	/**
	 * 先计算最里面括号里的内容
	 * @param string
	 * @return
	 */
	public static int get(String string) {
    	Stack<String> stack = new Stack<String>();
    	for (int i = 0; i < string.length(); i++) {
    		char chars = string.charAt(i);
		    if (chars == ')') {
		    	String str = "";
		    	while (!stack.isEmpty()) {
		    		String cha = stack.pop();
					str = cha + str;
					if ("(".equals(cha)) {
						break;
					}
				}
		    	int charRes = getResult(formatString(str.replace("(", "")));
		    	stack.push(String.valueOf(charRes));
			} else {
				stack.push(String.valueOf(chars));
			}
		}
    	StringBuffer stringBuffer = new StringBuffer();
    	while (!stack.isEmpty()) {
			stringBuffer.insert(0, stack.pop());
		}
		return getResult(formatString(stringBuffer.toString()));
	}
	
	
	/**
	 * 格式化算式
	 * 先计算连续乘或者连续除
	 * String string = "5-3+9*6*-6";
	 * @param string
	 * @return
	 */
	public static String formatString(String string) {
		Stack<String> stack1 = new Stack<String>();  //加减
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '*' || string.charAt(i) == '/') {
				int a = 0;
				if (!stack1.isEmpty()) {
					a = Integer.valueOf(stack1.pop());
					
				}
				String charb = "";
				int count = i + 1;
				for (int j = i + 1; j < string.length(); j++) {
					if (string.charAt(j) == '-' && j == i +1) {
						if (string.charAt(j + 1) >= '0' && '9' >= string.charAt(j + 1)) {
							charb = "-" + charb + String.valueOf(string.charAt(j + 1));
							count = j + 1;
							j = j + 1;
						} else {
							break;
						}
					} else {
						if (string.charAt(j) >= '0' && '9' >= string.charAt(j)) {
							charb = charb + String.valueOf(string.charAt(j));
							count = j;
						} else {
							break;
						}
					}
					
				}
				String b = String.valueOf(charb);
				if (string.charAt(i) == '*') {
					Integer result = Integer.valueOf(a) * Integer.valueOf(b);
					stack1.push(String.valueOf(result));
				} else {
					Integer result = Integer.valueOf(a) / Integer.valueOf(b);
					stack1.push(String.valueOf(result));
				}
				
				i = count;
			} else {
				String charb = String.valueOf(string.charAt(i));
				for (int j = i+1; j < string.length(); j++) {
					if (string.charAt(j) >= '0' && '9' >= string.charAt(j) && charb.charAt(0) >= '0' && '9' >= charb.charAt(0)) {
						charb = charb + String.valueOf(string.charAt(j));
						i = j;
					} else {
						break;
					}
				}
				stack1.push(charb);
			}
		}
		String str = "";
		while (!stack1.isEmpty()) {
			str = stack1.pop() + str;
		}
		str = str.replace("--", "+").replace("+-", "-").replace("-+", "-");
		return str;
	}
	
	/**
	 * 计算结果不含有连续符号计算的
	 * @param string
	 * @return
	 */
	public static int getResult(String string) {
		Stack<String> stack = new Stack<String>();
		Stack<Integer> stackRes = new Stack<Integer>();
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '*') {
				String a = "";
				while (!stack.isEmpty()) {
					a = stack.pop() + a;
				}
				if ("".equals(a)) {
					a = String.valueOf(stackRes.pop());
				}
				String charb = "";
				int count = i + 1;
				for (int j = i + 1; j < string.length(); j++) {
					if (string.charAt(j) >= '0' && '9' >= string.charAt(j)) {
						charb = charb + String.valueOf(string.charAt(j));
						count = j;
					} else {
						break;
					}
				}
				String b = String.valueOf(charb);
				Integer result = Integer.valueOf(a) * Integer.valueOf(b);
				stackRes.push(result);
				i=count;
			} else if (string.charAt(i) == '/') {
				String a = "";
				while (!stack.isEmpty()) {
					a = stack.pop() + a;
				}
				if ("".equals(a)) {
					a = String.valueOf(stackRes.pop());
				}
				String charb = "";
				int count = i + 1;
				for (int j = i + 1; j < string.length(); j++) {
					if (string.charAt(j) >= '0' && '9' >= string.charAt(j)) {
						charb = charb + String.valueOf(string.charAt(j));
						count = j;
					} else {
						break;
					}
				}
				String b = String.valueOf(charb);
				Integer result = Integer.valueOf(a) / Integer.valueOf(b);
				stackRes.push(result);
				i=count;
			} else if (string.charAt(i) == '+' || string.charAt(i) == '-') {
				if (string.charAt(0) == '-' && i == 0) {
					stack.push(String.valueOf(string.charAt(i)));
				} else {
					if (stack.isEmpty()) {
						stack.push(String.valueOf(string.charAt(i)));
					} else {
						String charb = "";
						while (!stack.isEmpty()) {
							charb = stack.pop() + charb;
						}
						Integer result = Integer.valueOf(charb);
						stackRes.push(result);
						stack.push(String.valueOf(string.charAt(i)));
					}
					
				}
			} else {
				stack.push(String.valueOf(string.charAt(i)));
			}
		}
		int count = 0;
		String str = "";
		while (!stack.isEmpty()) {
			str = stack.pop() + str;
		}
		if (!"".equals(str)) {
			count = Integer.valueOf(str);
		}
		while (!stackRes.isEmpty()) {
			count = count + stackRes.pop();
		}
		return count;
	}
	
	public static void main(String[] args) {
		String string = "3+2*{1+2*[-4/(8-6)+7]}";
		string = string.replace("[", "(").replace("]", ")").replace("{", "(").replace("}", ")");
		System.out.println(get(string));
	}
}
