package com.cloud.utils;

/**
 * <p>Title: MathUtils</p>
 * <p>Description: 处理算术通用工具类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 深圳雁联计算系统有限公司</p>
 * @author not attributable
 * @version 1.0
 */

public class MathUtils {
  /**
   *解析整数
   *@param value 解析字符串
   **/
  public static int parseInt(String value){
      return Integer.parseInt(value);
  }

  public static void main(String[] value){
      MathUtils.parseInt("97w");
  }

  /**
   *解析双精度
   *@param value 解析字符串
   *@param dot 保留小数的位数 取值范围10,100,1000,10000
           分别表示 保留1,2,3,4位小数
   **/
  public static double parseDouble(String value, int dot) {
      double dvalue = Double.parseDouble(value);
      dvalue = Math.round(dvalue * dot) / (double) dot;
      return dvalue;
  }

  /**
   *解析双精度(保留两位小数)
   *@param value 解析字符串
   **/
  public static double parseDouble(String value) {
    return parseDouble(value, 100);
  }

  /**
   *解析浮点数
   *@param value 解析字符串
   *@param dot 保留小数的位数 取值范围10,100,1000,10000
           分别表示 保留1,2,3,4位小数
   **/
  public static float parseFloat(String value, int dot) {
      float fvalue = Float.parseFloat(value);
      fvalue = Math.round(fvalue * dot) / (float) dot;
      return fvalue;
  }

  /**
   *解析浮点数(保留两位小数)
   *@param value 解析字符串
   **/
  public static float parseFloat(String value) {
    return parseFloat(value, 100);
  }


  /**
   *两数相加
   *@param augend 被加数
   *@param addend 加数
   **/
  public static String sum(String augend, String addend) {
    String str_augend = "";
    String str_addend = "";
    if (augend.length() >= 2)
      str_augend = augend.substring(0, 2);
    if (addend.length() >= 2)
      str_addend = addend.substring(0, 2);
    if ("--".equals(str_augend))
      augend = augend.substring(2);
    if ("--".equals(str_addend))
      addend = addend.substring(2);
      double d_augend = parseDouble(augend);
      double d_addend = parseDouble(addend);
      double sum = d_augend + d_addend;
      return parseDouble(Double.toString(sum)) + "";
  }



  /**
   *两数相乘
   *@param faciend       被乘数
   *@param multiplier    乘数
   *@param dot 保留小数的位数 取值范围10,100,1000,10000
          分别表示 保留1,2,3,4位小数
   **/
  public static String mul(String faciend, String multiplier, int dot) {
      double d_faciend = Double.parseDouble(faciend);
      double d_multiplier = Double.parseDouble(multiplier);
      double d_amass = d_faciend * d_multiplier;
      d_amass = Math.round(d_amass * dot) / (double) dot;
      return d_amass + "";
  }

  /**
   *两数相乘(保留两位小数)
   *@param faciend 被乘数
   *@param multiplier 乘数
   **/
  public static String mul(String faciend, String multiplier) {
    return mul(faciend, multiplier, 100);
  }

  /**
   *两数相除
   *@param dividend 被除数
   *@param divisor  除数       quotient  商数
   *@param dot 保留小数的位数 取值范围10,100,1000,10000
          分别表示 保留1,2,3,4位小数
   **/
  public static String rate(String dividend, String divisor, int dot) {
      double d_dividend = Double.parseDouble(dividend);
      double d_divisor = Double.parseDouble(divisor);
      double d_quotient = d_dividend / d_divisor;
      d_quotient = Math.round(d_quotient * dot) / (double) dot;
      return d_quotient + "";
  }

  /**
   *两数相除(保留两位小数)
   *@param dividend 被除数
   *@param divisor 除数
   **/
  public static String rate(String dividend, String divisor) {
    return rate(dividend, divisor, 100);
  }

  /**
   *两数的百分数计算
   *@param a 分子 字符串
   *@param b 分母 字符串
   **/

  public static String percent(String a, String b) {
    double d_a = 0;
    double d_b = 0;
    double d_rate = 0;
    String rate = "";

    try {
      if (b == null || b == "0" || b.trim().length() <= 0)
        return "-";
    }
    catch (Exception e) {

    }

    try {
      d_a = Double.parseDouble(a);
    }
    catch (Exception e) {
      d_a = 0;
    }
    try {
      d_b = Double.parseDouble(b);
    }
    catch (Exception e) {
      d_b = 0;
    }
    try {
      d_rate = (d_a / d_b);
      rate = (Math.round(d_rate * 10000)) / 100 + "%";
    }
    catch (Exception e) {
      rate = "0%";
    }
    if (rate.equals("0%"))
      rate = "-";
    return rate;
  }
}
