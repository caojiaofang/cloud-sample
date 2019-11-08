/*
 * 文件名：EncryptionKeyer.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： EncryptionKeyer.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月31日
 * 修改内容：新增
 */
package com.cloud.web;
import java.io.PrintStream;
import java.util.Calendar;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
public class EncryptionKeyer
{
  private static int[] CODE_AE = { 
    0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 
    12, 13, 14, 15, 16, 17, 18, 19, 60, 61, 50, 
    38, 24, 25, 63, 39, 28, 29, 30, 31, 64, 90, 
    127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 
    96, 75, 97, 240, 241, 242, 243, 244, 245, 246, 247, 
    248, 249, 122, 94, 76, 126, 110, 111, 124, 193, 194, 
    195, 196, 197, 198, 199, 200, 201, 209, 210, 211, 212, 
    213, 214, 215, 216, 217, 226, 227, 228, 229, 230, 231, 
    232, 233, 74, 224, 79, 95, 109, 121, 129, 130, 131, 
    132, 133, 134, 135, 136, 137, 145, 146, 147, 148, 149, 
    150, 151, 152, 153, 162, 163, 164, 165, 166, 167, 168, 
    169, 192, 106, 208, 161, 7, 32, 33, 34, 35, 36, 
    21, 6, 23, 40, 41, 42, 43, 44, 9, 10, 27, 
    48, 49, 26, 51, 52, 53, 54, 8, 56, 57, 58, 
    59, 4, 20, 62, 225, 65, 66, 67, 68, 69, 70, 
    71, 72, 73, 81, 82, 83, 84, 85, 86, 87, 88, 
    89, 98, 99, 100, 101, 102, 103, 104, 105, 112, 113, 
    114, 115, 116, 117, 118, 119, 120, 128, 138, 139, 140, 
    141, 142, 143, 144, 154, 155, 156, 157, 158, 159, 160, 
    170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 
    181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 
    202, 203, 204, 205, 206, 207, 218, 219, 220, 221, 222, 
    223, 234, 235, 236, 237, 238, 239, 250, 251, 252, 253, 
    254, 255 };

  private static byte[] GetKey()
  {
    byte[] keyval = new byte[3];

    Calendar rightNow = Calendar.getInstance();
    int dayofy = 438;//rightNow.get(6) + 137;
	System.out.println(dayofy);
    String sint = new String();
    sint = Integer.toString(dayofy);
	System.out.println("sint[" + sint + "]");
    keyval[0] = ((byte)CODE_AE[sint.charAt(2)]);
    keyval[1] = ((byte)CODE_AE[sint.charAt(1)]);
    keyval[2] = ((byte)CODE_AE[sint.charAt(0)]);
	System.out.println("keyval112222222[" +  keyval[0] + "]");
	System.out.println(sint.charAt(2));
	System.out.println("keyval[" + keyval + "]");
    return keyval;
  }



  public static byte[] EnByteDatas(byte[] buf)
  {
    int len = buf.length;
	System.out.println("buflen[" + len + "]");
    int loop = len / 3;
    int remainder = len % 3;
	System.out.println("loop[" + loop + "]");
	System.out.println("remainder[" + remainder + "]");
    byte[] keyval = GetKey();
	
    for (int i = 0; i < loop; i++)
      for (int j = 0; j < 3; j++)
      {
        int tmp38_37 = (i * 3 + j);
        byte[] tmp38_32 = buf; tmp38_32[tmp38_37] = ((byte)(tmp38_32[tmp38_37] ^ keyval[j]));
      }
    for (int j = 0; j < remainder; j++)
    {
      int tmp76_75 = (loop * 3 + j);
      byte[] tmp76_69 = buf; tmp76_69[tmp76_75] = ((byte)(tmp76_69[tmp76_75] ^ keyval[j]));
    }
	System.out.println("buf11111111111111[" + buf + "]");
    return buf;
  }


  public byte[] addKeyToByte(byte[] b) {
    return EnByteDatas(b);
  }

  public void subkey(byte[] b, int len) {
    EnByteDatas(b);
  }

  public static byte[] EnDatas(String s)
  {
    byte[] byte_Stream = s.getBytes();
    return EnByteDatas(byte_Stream);
  }

  public String UnEnDatas(byte[] byte_Stream) {
    return new String(EnByteDatas(byte_Stream));
  }

  public static String fillStr(String value, String fillChar, int len) {

		while (value.length() < len) {
			value = fillChar + value;
		}

		return value;
	}
  
  
  public static final String UTF_8 = "GBK";
  
  public static void main(String[] args) throws UnsupportedEncodingException {
		String string = "奶寱熢帠唻殯栁稚菽谟憱悰湚殶沃煈扑缕谔誓憶毻葓問葡煗娛臐晻柺垉乃嗜诐晻柺膮晹啈泼娜萌讌晹啈葡蹞枍葡煗娛臐晻柺垉奶嗜诐晻柺膮晹啈泼拍軅櫉仢腿讟憗腿摉嵠蠚櫈懫儎绕颇軞櫈懫蟼櫉仢偷让娜媚让派们退茸厱攩懫羡摉嵠蠠潑誓潟晼蕡兡吓茸潟晼誓厱攩懫媚耸廖亮拖蹘挊崠誓軣潑誓槕佂葨挋澩剤猛认蹡挋澩葞挊崠仕滥嚷茸厱攩懫羡摉嵠蠠潑誓潟晼蕡兣汕茸潟晼誓厱攩懫聊擅咆釉赜载釉芈派屡陕派屡赜载釉赜郧淘赜载铀翘素釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜匀媚让娜媚让娜媚让娜媚让娜媚让娜媚让娜媚让娜媚让娜媚赜载釉让娜媚让娜媚让娜媚让娜媚让茸厱攩懫羡摉嵠蠠潑誓潟晼蕡兣膳茸潟晼誓厱攩懫釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载趟赜载釉让娜媚让茸厱攩懫羡摉嵠蠠潑誓潟晼蕡兣伤茸潟晼誓厱攩懫幽让娜媚让娜媚让那趟翘饲趟翘素釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载釉赜载尤讌晹啈葡蹞枍葡蹐問葡蹥湒芣";

		string = "<?xml version=\\\"1.0\\\" encoding=\\\"gb2312\\\"?><bob><pub><key><name>pp039</name><value>0000</value></key><key><name>pp049</name><value>01</value></key><key><name>pp052</name><value>A00000000110358</value></key><key><name>pp076</name><value>003956255</value></key><key><name>pp090</name><value>33001</value></key><key><name>pp114</name><value>20101          11111111111         测试      一般帐户                                                                                                                                                                                                000000000000000000000000000000000000000000000000000      00000000000000000000000000</value></key><key><name>pp116</name><value>                                                                                                                                                                                                                                                                                                                                                                                                                               正常      00000000</value></key><key><name>pp118</name><value> 0000000000000000北京华清恒信科技有限公司                                                                                                                                                                                </value></key></pub></bob>";
		EncryptionKeyer ec = new EncryptionKeyer();

		byte[] resultByte = string.getBytes();
		resultByte = ec.addKeyToByte(resultByte);
		
		System.err.println("加密:" + new String(resultByte));

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String string2 = new String(resultByte);
		byte[] a = string2.getBytes();
		ec.subkey(a, a.length);
		System.out.println("解密:" + new String(a));
  }
  
}