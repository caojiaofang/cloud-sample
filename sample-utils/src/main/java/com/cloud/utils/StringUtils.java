package com.cloud.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.utils.date.DateUtils;




/**
 * 工具类
 * Title: StringUtils.java
 * Description: 
 * Company: 
 * @author lizhi
 * @date 2017年12月14日下午6:01:30
 */
public abstract class StringUtils {
	private final static Logger logger = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * 路径分割符号
	 */
	private static final String CHANGE_PATH = "/"; // folder sep.

	private static final String WIN_CHANGE_PATH = "\\"; // Windows folder sep.

	/**
	 * 路径位置符
	 */
	private static final String TOP_PATH = ".."; // Top folder

	private static final String CURRENT_PATH = "."; // Current folder

	public static boolean isNEmpty(String str) {
		if ((str != null) && (str.trim().length() > 0) && (!"null".equals(str))) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		return !isNEmpty(str);
	}

	public static String conversionNullToBlank(String str) {
		if (isNotNull(str)) {
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 将Ascii转换为String
	 * 
	 * @param str
	 * @return
	 */
	public static String asciiToString(String str) {
		if (isNotNull(str)) {
			return String.valueOf((char) (Integer.parseInt(str) + 64));
		} else {
			return String.valueOf((char) 65);
		}
	}

	public static boolean isNull(String value) {
		return !hasLength(value);
	}

	public static boolean isNull(Object value) {
		return !hasLength(value);
	}

	/**
	 * 效验字符串是否不为空
	 * 
	 * @param value
	 * @return true 表示 value不为空(value!=null && !"".equals(value))
	 */
	public static boolean isNotNull(String value) {
		return hasLength(value);
	}

	public static boolean isNotNull(Object value) {
		return hasLength(value);
	}

	/**
	 * 验证字符串是否有长度 当字符串为"null"或"NULL"返回值是false 当字符串为空格返回值是true Check if a String
	 * has length.
	 * <p>
	 * 
	 * <pre>
	 *  StringUtils.hasLength(null) = false
	 *  StringUtils.hasLength(&quot;&quot;) = false
	 *  StringUtils.hasLength(&quot;null&quot;) = false
	 *  StringUtils.hasLength(&quot;NULL&quot;) = false
	 *  StringUtils.hasLength(&quot; &quot;) = true
	 *  StringUtils.hasLength(&quot;Hello&quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0 && !"null".equals(str.toLowerCase()));
	}

	public static boolean hasLength(Object obj) {
		return (obj != null && (obj + "").length() > 0 && !"null".equals((obj + "").toLowerCase()));
	}

	/**
	 * 验证字符串是否有文本 当字符串为"null"或"NULL"返回值是false 当字符串为空格返回值是false Check if a String
	 * has text. More specifically, returns <code>true</code> if the string not
	 * <code>null<code>, it's <code>length is > 0</code>, and it has at least
	 * one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 *   StringUtils.hasText(null) = false
	 *   StringUtils.hasText(&quot;&quot;) = false
	 *   StringUtils.hasText(&quot; &quot;) = false
	 *   StringUtils.hasText(&quot;12345&quot;) = true
	 *   StringUtils.hasText(&quot; 12345 &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not null, length > 0, and not
	 *         whitespace only
	 */
	public static boolean hasText(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || "null".equals(str.toLowerCase())) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回字符串中包含子字符串的数量
	 * 
	 * @param s
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countSubString(String s, String sub) {
		if (s == null || sub == null || "".equals(sub)) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = s.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * 字符串替换 Replace all occurences of a substring within a string with another
	 * string.
	 * 
	 * @param inString
	 *            String to examine 输入字符串
	 * @param oldPattern
	 *            String to replace 替换目标
	 * @param newPattern
	 *            String to insert 替换值
	 * @return a String with the replacements 替换结果
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // Our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/**
	 * 删除字符串 Delete all occurrences of the given substring.
	 * 
	 * @param pattern
	 *            the pattern to delete all occurrences of
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * 删除字符串中指定的字符集.
	 * 例如:deleteAny("abcdefghijklmnnxxyyx","abfkx")="cdeghijlmnnyy"
	 * 
	 * @param chars
	 *            characters to delete. E.g. az\n will delete as, zs and new
	 *            lines.
	 */
	public static String deleteAny(String inString, String chars) {
		if (inString == null || chars == null) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (chars.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	/**
	 * 将字符串按指定的"分割符"分割成字符串数组(忽略连续出现分割符部分),同时可设定是否进行去空格处理,是否忽略空字符串 Tokenize the
	 * given String into a String array via a StringTokenizer.
	 * 
	 * @param s
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String
	 * @param trimTokens
	 *            trim the tokens via String.trim 是否进行去除空格操作
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array 是否忽略空字符串
	 * @return an array of the tokens 忽略连续出现分割符部分
	 *         split("a;b;c;;",true,true)={'a','b','c'}
	 *         split("a;b;c;;",false,true)={'a','b','c'}
	 * 
	 *         split("a;b;c;;",false,false)={'a','b','c'} split("a;b;c; ;
	 *         ",false,true)={'a','b','c'}
	 * 
	 *         split(" a;b;c; ; ",false,false)={' a','b','c',' ',' '} split("
	 *         a;b;c; ; ",true,false)={'a','b','c','',''}
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 */
	public static String[] split(String s, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(s, delimiters);
		List<String> tokens = Lists.newArrayList();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!(ignoreEmptyTokens && token.length() == 0)) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * 将字符串按指定的"分割符"分割成字符串数组(接受连续出现分割符部分) Take a String which is a delimited
	 * list and convert it to a String array.
	 * 
	 * @param s
	 *            String
	 * @param delim
	 *            delim (this will not be returned)
	 * @return an array of the tokens in the list
	 *         split("a;b;c;;")={'a','b','c','',''} split(" a;b;c; ; ")={'
	 *         a','b','c',' ',' '}
	 */
	public static String[] split(String s, String delim) {
		if (s == null) {
			return new String[0];
		}
		if (delim == null) {
			return new String[] { s };
		}

		List<String> l = new LinkedList<String>();
		int pos = 0;
		int delPos = 0;
		while ((delPos = s.indexOf(delim, pos)) != -1) {
			l.add(s.substring(pos, delPos));
			pos = delPos + delim.length();
		}
		if (pos <= s.length()) {
			// add rest of String
			l.add(s.substring(pos));
		}
		return (String[]) l.toArray(new String[l.size()]);
	}

	/**
	 * 将字符串按指定的"分割符"分割成数值数组
	 * 
	 * @param s
	 * @param delim
	 * @return
	 */
	public static int[] splitToIntArray(String s, String delim) {
		String[] stringValueArray = split(s, delim);
		int[] intValueArray = new int[stringValueArray.length];
		for (int i = 0; i < intValueArray.length; i++) {
			intValueArray[i] = Integer.parseInt(stringValueArray[i]);
		}
		return intValueArray;
	}

	/**
	 * Convenience method to convert a string list to a Set. Note that this will
	 * suppress duplicates.
	 * 
	 * @param s
	 *            String that 'value1,value2,value3'
	 * @return a Set of String entries in the list
	 */
	public static Set<String> splitToSet(String s, String delim) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = split(s, delim);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * CSV格式字符串转换为字符串数组 Convert a CSV list into an array of Strings.
	 * 
	 * @param s
	 *            CSV list
	 * @return an array of Strings, or the empty array if s is null
	 */
	public static String[] csvStringToArray(String s) {
		return split(s, ",");
	}

	/**
	 * CSV格式字符串转换为Set数据结构 Convenience method to convert a CSV string list to a
	 * set. Note that this will suppress duplicates.
	 * 
	 * @param s
	 *            CSV String
	 * @return a Set of String entries in the list
	 */
	public static Set<String> csvStringToSet(String s) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = csvStringToArray(s);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 */
	public static String joinCsvString(Object[] arr) {
		return join(arr, ",");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param c
	 *            Collection to display
	 */
	public static String joinCsvString(Collection<String> c) {
		return join(c, ",");
	}

	/**
	 * Convenience method to return a Map as a CSV String. E.g. useful for
	 * toString() implementations.
	 * 
	 * @param c
	 *            Collection to display
	 */
	public static String joinCsvString(Map<String, String> map) {
		return join(map, ",");
	}

	/**
	 * 将对象数组值按指定分割符转化为字符串 转换的字符串为：value1,value2,value3 分隔符为：","
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 * @param delim
	 *            delimiter to use (probably a ,)
	 */
	public static String join(Object[] arr, String delim) {
		if (arr == null) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				if (i > 0) {
					sb.append(delim);
				}
				sb.append(arr[i]);
			}
			return sb.toString();
		}
	}

	/**
	 * 将Collection值按指定分割符转化为字符串 转换的字符串为：value1,value2,value3 分隔符为：","
	 * 
	 * @param c
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String join(Collection<String> c, String delim) {
		if (c == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = c.iterator();
		int i = 0;
		Object objValue = null;
		while (it.hasNext()) {
			if (i++ > 0) {
				sb.append(delim);
			}
			objValue = it.next();
			sb.append(objValue == null ? " " : objValue);
		}
		return sb.toString();
	}

	/**
	 * 将Map值按指定分割符转化为字符串 转换的字符串为：key1=value1,key2=value2,key3=value3 分隔符为：","
	 * 
	 * @param c
	 *            HashMap to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String join(Map<String, String> map, String delim) {
		if (map == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Iterator<String> itrKeys = map.keySet().iterator();
		int i = 0;
		String strKey = "";
		String strValue = "";
		while (itrKeys.hasNext()) {
			strKey = (String) itrKeys.next();
			strValue = (String) map.get(strKey);
			if (i++ > 0) {
				sb.append(delim);
			}
			sb.append(strKey + "=" + strValue);
		}
		return sb.toString();
	}

	/**
	 * 追加字符串到String数组里面
	 * 
	 * @param arr
	 *            数组
	 * @param s
	 *            要追加的字符串
	 * @return 新数组
	 */
	public static String[] addToArray(String[] arr, String s) {
		String[] newArr = new String[arr.length + 1];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		newArr[arr.length] = s;
		return newArr;
	}

	/**
	 * 获得字符串中,指定分割符号的最后一段 可以用于获得类名 Unqualify a string qualified by a '.' dot
	 * character. For example, "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * 获得字符串中,指定分割符号的最后一段 可以用于获得文件路径中的路径名称 Unqualify a string qualified by a
	 * separator character. For example, "this:name:is:qualified" returns
	 * "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param separator
	 *            the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * 把第一个字符变大写 Capitalize a <code>String</code>, changing the first letter to
	 * upper case as per {@link Character#toLowerCase(char)}. No other letters
	 * are changed.
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return the capitalized String, <code>null</code> if null
	 */

	public static String capitalizeFirst(String str) {
		return changeFirstCharacterCase(true, str);
	}

	/**
	 * 把第一个字符变小写 Uncapitalize a <code>String</code>, changing the first letter
	 * to lower case as per {@link Character#toLowerCase(char)}. No other
	 * letters are changed.
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalizeFirst(String str) {
		return changeFirstCharacterCase(false, str);
	}

	private static String changeFirstCharacterCase(boolean capitalize, String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(strLen);
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * 统一路径格式为"a/b/c" Normalize the path by suppressing sequences like "path/.."
	 * and inner simple dots folders.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that
	 * Windows separators ("\") are replaced by simple dashes.
	 * 
	 * @param path
	 *            The original path
	 * @return The normalized path
	 */

	public static String cleanPath(String path) {
		String p = replace(path, WIN_CHANGE_PATH, CHANGE_PATH);
		String[] pArray = split(p, CHANGE_PATH);
		List<String> pList = new LinkedList<String>();
		int tops = 0;
		for (int i = pArray.length - 1; i >= 0; i--) {
			if (CURRENT_PATH.equals(pArray[i])) {
				// Do nothing
			} else if (TOP_PATH.equals(pArray[i])) {
				tops++;
			} else {
				if (tops > 0) {
					tops--;
				} else {
					pList.add(0, pArray[i]);
				}
			}
		}
		return join(pList, CHANGE_PATH);
	}

	/**
	 * 从Blob大对象中获得字符串信息
	 * 
	 * @param blob
	 * @return
	 */
	public static StringBuffer getStrByBlob(java.sql.Blob blob) throws IOException, java.sql.SQLException {
		StringBuffer buf = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(blob.getBinaryStream()));
		String tmp = "";
		while (in != null && (tmp = in.readLine()) != null) {
			buf.append(tmp);

		}
		in = null;
		tmp = null;
		return buf;
	}

	/**
	 * 从Clob大对象中获得字符串信息
	 * 
	 * @param clob
	 * @return
	 */
	public static StringBuffer getStrByClob(java.sql.Clob clob) throws IOException, java.sql.SQLException {
		StringBuffer buf = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(clob.getAsciiStream()));
		String tmp = "";
		while (in != null && (tmp = in.readLine()) != null) {
			buf.append(tmp);
		}
		in = null;
		tmp = null;
		return buf;
	}

	/**
	 * 压缩字符串
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static String compress(String str) throws Exception {
		ByteArrayOutputStream data_ = null;
		String base64Src = "";
		// Create byte array outputstream
		data_ = new ByteArrayOutputStream();
		// Create gZip compressing inputstream
		DataOutputStream out = new DataOutputStream(new GZIPOutputStream(data_));
		out.write(str.getBytes());
		out.close();
		// Return compressint byte stream
		data_.close();
		base64Src = new String(new Base64().encode(data_.toByteArray()));
		return base64Src;
	}

	/**
	 * 解压缩字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String deCompress(String str) throws Exception {
		String object_ = null;
		byte[] byteSrc64 = new Base64().decode(str);
		// Create byte array inputstream
		// ByteArrayInputStream bi = new ByteArrayInputStream(byteSrc64);
		// Create gZip decompressing inputstream
		GZIPInputStream gzIn = new GZIPInputStream(new ByteArrayInputStream(byteSrc64));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = gzIn.read(buf)) >= 0) {
			baos.write(buf, 0, len);
		}
		object_ = new String(baos.toByteArray());
		baos.close();
		gzIn.close();
		return object_;
	}

	/**
	 * BASE64 的编码 将字符串进行 BASE64 编码
	 * 
	 * @param s
	 * @return
	 */
	public static String encodedBase64(String s) {
		if (s == null) {
			return null;
		}
		return new String((new Base64()).encode(s.getBytes()));
	}

	/**
	 * BASE64 的解码 将 BASE64 编码的字符串进行解码
	 * 
	 * @param s
	 * @return
	 */
	public static String decodedBase64(String s, String code) {
		if (s == null) {
			return null;
		}
		try {
			byte[] b = new Base64().decode(s);
			if (code != null)
				return new String(b, code);
			else
				return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 数据库到对象中文转码处理
	 * 
	 * @param 需转码的对象，一般为字符串
	 * @return 返回ISO8859-1字符串
	 */
	public static String app2DbEncode(String value) {
		if (value == null || value.equals("null")) {
			value = "";
			// return value;
		}
		try {
			return new String(value.getBytes("ISO8859-1"));
		} catch (Exception e) {
			return value + "";
		}
	}

	/**
	 * 数据库到对象中文转码处理
	 * 
	 * @param 需转码字符串
	 * @return 返回ISO8859-1字符串
	 */
	public static String db2AppEncode(String value) {
		if (value == null || value.equals("null")) {
			value = "";
			// return value;
		}
		try {
			return new String(value.getBytes("ISO8859-1"));
		} catch (Exception e) {
			return value + "";
		}
	}

	/**
	 * unicodeToGB
	 * 
	 * @param strIn
	 * @return
	 */
	public static String unicodeToGB(String strIn) {
		if (strIn == null || strIn.equals("")) {
			return strIn;
		}

		String strOut;
		try {
			strOut = new String(strIn.getBytes("GBK"), "ISO8859_1");
			return strOut;
		} catch (UnsupportedEncodingException e) {
			logger.info("Unsupported Encoding at CharsetConvert.unicodeToGB()");
			return strIn;
		}
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * GBToUnicode
	 * 
	 * @param strIn
	 * @return
	 */
	public static String GBToUnicode(String strIn) {
		if (strIn == null || strIn.equals("")) {
			return strIn;
		}

		String strOut;
		try {
			strOut = new String(strIn.getBytes("ISO8859_1"), "GBK");
			return strOut;
		} catch (UnsupportedEncodingException e) {
			logger.info("Unsupported Encoding at CharsetConvert.GBToUnicode()");
			return strIn;
		}
	}

	/**
	 * UTF8ToUnicode
	 * 
	 * @param strIn
	 * @return
	 */
	public static String UTF8ToUnicode(String strIn) {
		if (strIn == null || strIn.equals("")) {
			return strIn;
		}

		String strOut;
		try {
			strOut = new String(strIn.getBytes("ISO8859_1"), "UTF-8");
			return strOut;
		} catch (UnsupportedEncodingException e) {
			logger.info("Unsupported Encoding at CharsetConvert.GBToUnicode()");
			return strIn;
		}
	}

	/**
	 * html字符串编码转换
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlEncode(String str) {
		if (!isNotNull(str))
			return "";
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("'", "&apos;");
		return str;
	}

	// 删除开头内容
	private static String deleteStart(final String str, final String start) {
		String kaitou = str;
		// 判断是否以 &nbsp; 开头 开头删除
		while (kaitou.length() > 0 && kaitou.indexOf(start) == 0) {
			kaitou = kaitou.substring(start.length());

		}
		return kaitou;
	}

	// 删除结尾内容
	private static String deleteEnd(final String str, final String End) {
		String gaihou = str;
		while (gaihou.length() >= End.length() && gaihou.endsWith(End)) {
			gaihou = gaihou.substring(0, gaihou.length() - End.length());
		}
		return gaihou;
	}

	// 删除字符串头的空格和<p>和结尾的</p>符号
	public static String deleteHtmlTag(String str) {
		String genggai = str;
		genggai = deleteStart(genggai, "&nbsp;");
		genggai = deleteStart(genggai, "<p>");
		genggai = deleteEnd(genggai, "&nbsp;");
		genggai = deleteEnd(genggai, "</p>");
		return genggai;
	}

	/**
	 * 自动产生指定位数的唯一键值 编码规则为 年月日 随机编码(xxxx xxxx xxxxxxxx...)
	 * 
	 */
	public static String getId(int n) {
		// String tm = System.currentTimeMillis() + "";

		String rd1 = Math.random() + "";
		String rd2 = Math.random() + "";
		String rd3 = Math.random() + "";
		String id = IDGen.getId();
		id += rd1.replaceAll("\\.", "");
		id += rd2.replaceAll("\\.", "");
		id += rd3.replaceAll("\\.", "");
		return id.substring(0, n);

	}

	/**
	 * 自动产生指定位数的
	 * 
	 */
	public static String getRandom(int n) {
		String rd1 = NumberFormatUtils.formatNumber(Math.random(), "##.#####") + "";
		String rd2 = NumberFormatUtils.formatNumber(Math.random(), "##.#####") + "";
		String rd3 = NumberFormatUtils.formatNumber(Math.random(), "##.#####") + "";
		String rd4 = NumberFormatUtils.formatNumber(Math.random(), "##.#####") + "";
		String id = "";
		id += rd1.replaceAll("\\.", "");
		id += rd2.replaceAll("\\.", "");
		id += rd3.replaceAll("\\.", "");
		id += rd4.replaceAll("\\.", "");
		return id.substring(0, n);

	}

	/**
	 * 自动产生默认16位的唯一键值 编码规则为 年月日 随机编码(xxxx xxxx xxxxxxxx)
	 */
	public static String getId() {
		return IDGen.getId();
	}

	public static String joinTag(int count, String tag) {
		String temp = "";
		for (int i = 0; i < count; i++) {
			temp += tag;
		}
		return temp;
	}

	/**
	 * 获取URL参数
	 * 
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getURLParameter(String url, String name) {
		String js_get = url;
		// kevin 修正形如：
		// "http://localhost:8080/simpro/index.jsp?dfd=sf&fd=adfad","fd"
		int __start = js_get.indexOf("?" + name + '=');
		int _start = js_get.indexOf("&" + name + '=');
		int start = -1;
		if (__start > -1) {
			start = __start;
		} else if (_start > -1) {
			start = _start;
		}
		if (start == -1)
			return "";
		int len = start + name.length() + 2;
		int end = js_get.indexOf('&', len);
		if (end == -1)
			end = js_get.length();
		return js_get.substring(len, end);
	}

	public static String addURLParameter(String url, String parameterName, String parameterValue) {
		if (url.indexOf("?") > -1) {
			url = url + "&" + parameterName + "=" + parameterValue;
		} else {
			url = url + "?" + parameterName + "=" + parameterValue;
		}
		return url;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to
	 *        isBlank(CharSequence)
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty (""), not null and not whitespace
	 * only.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null and
	 *         not whitespace
	 * @since 2.0
	 * @since 3.0 Changed signature from isNotBlank(String) to
	 *        isNotBlank(CharSequence)
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	public static String IDYMDHMSGen(int n) {
		java.util.Date date = new java.util.Date();
		String id = "";
		String ymd = DateUtils.format(date, DateUtils.YEAR_MONTH_DAY_PATTERN_BLANK);

		int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int m = Calendar.getInstance().get(Calendar.MINUTE);
		int s = Calendar.getInstance().get(Calendar.SECOND);
		id += ymd + (h < 10 ? "0" + h : h) + (m < 10 ? "0" + m : m) + (s < 10 ? "0" + s : s);
		String rd1 = Math.random() + "";
		String rd2 = Math.random() + "";
		String rd3 = Math.random() + "";
		String rd4 = Math.random() + "";
		id += rd1.replaceAll("\\.", "");
		id += rd2.replaceAll("\\.", "");
		id += rd3.replaceAll("\\.", "");
		id += rd4.replaceAll("\\.", "");
		return id.substring(0, n);
	}

	/**
	 * <p>
	 * Title:IDGen
	 * </p>
	 * <p>
	 * Description: 唯一编码生成器
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2005
	 * </p>
	 * <p>
	 * Company: 北京瑞驰博通网络技术有限公司
	 * </p>
	 * 
	 * @author not attributable
	 * @version 1.0
	 */
	static class IDGen {
		static private int count = 0;

		static private int MAXCONT = 999;

		static private String getId() {
			count++;
			if (count >= MAXCONT) {
				count = 0;
			}
			java.util.Date date = new java.util.Date();
			String id = "";
			String ymd = DateUtils.format(date, DateUtils.YEAR_MONTH_DAY_PATTERN_BLANK);
			int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 * 60;
			int m = Calendar.getInstance().get(Calendar.MINUTE) * 60;
			int s = Calendar.getInstance().get(Calendar.SECOND);
			String hms = h + m + s + "";
			int offsetLength = 5 - hms.length();
			for (int i = 0; i < offsetLength; i++) {
				hms = "0" + hms;

			}
			String countStr = count + "";
			offsetLength = (MAXCONT + "").length() - countStr.length();
			for (int i = 0; i < offsetLength; i++) {
				countStr = "0" + countStr;
			}
			id = ymd + hms + countStr;
			return id;
		}
	};
	
	public static String formatLeftString(String str, int length, String s) {
		int t = StringUtils.isEmpty(str) ? length : length - str.length();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < t; ++i) {
			sb.append(s);
		}

		return sb.append(str).toString();
	}

	public static String formatLeftSpace(String str, int length) {
		return formatLeftString(str, length, " ");
	}

	public static String formatLeftZero(String str, int length) {
		return formatLeftString(str, length, "0");
	}

	public static String formatLeftZero(long val, int length) {
		return String.format("%0" + length + "d", val);
	}

	public static String formatRightEmpty(String str, int length) {
		return String.format("%-" + length + "s", str);
	}

}
