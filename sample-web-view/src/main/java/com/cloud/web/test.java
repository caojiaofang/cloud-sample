/*
 * 文件名：test.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： test.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年11月1日
 * 修改内容：新增
 */
package com.cloud.web;

import java.util.Scanner;

/**
 * @Title:  test.java
 * @Package: com.cloud.web.test.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年11月1日 上午10:44:29
 */
public class test {
	public static int getCount(String str,char c){
        int count = 0;
        if(str != null && str.length() > 0){
            for(int i = 0;i < str.length();i++){
                if(c == str.charAt(i)){
                    count++;
                }
            }
        }else{
            count = 0;
        }
        return count;
    }
     
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
            String str = s.next();
            char c = s.next().charAt(0);
            int i = getCount(str,c);
            System.out.println(i);
    }
}
