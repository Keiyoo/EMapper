/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.emapper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：字符串处理类.
 */
public final class StrUtils {
	public static final int UNIT_MB = 1048576;
	public static final int UNIT_KB = 1024;

	/**
	 * 描述：将null转化为“”.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 字符串的String类型
	 */
	public static String parseEmpty(String str) {
		if (str == null || "null".equals(str.trim())) {
			str = "";
		}
		return str.trim();
	}

	/**
	 * 描述：判断一个字符串是否为null或空值.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !"".equals(str.trim());
	}

	/**
	 * 获取字符串中文字符的长度（每个中文算2个字符）.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 中文字符的长度
	 */
	public static int chineseLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		if (!isEmpty(str)) {
			for (int i = 0; i < str.length(); i++) {
				/* 获取一个字符 */
				String temp = str.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					valueLength += 2;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 描述：获取字符串的长度.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 字符串的长度（中文字符计2个）
	 */
	public static int strLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					// 中文字符长度为2
					valueLength += 2;
				} else {
					// 其他字符长度为1
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 描述：获取指定长度的字符所在位置.
	 * 
	 * @param str
	 *            指定的字符串
	 * @param maxL
	 *            要取到的长度（字符长度，中文字符计2个）
	 * @return 字符的所在位置
	 */
	public static int subStringLength(String str, int maxL) {
		int currentIndex = 0;
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
			if (valueLength >= maxL) {
				currentIndex = i;
				break;
			}
		}
		return currentIndex;
	}

	/**
	 * 描述：手机号格式验证.
	 * 
	 * @param str
	 *            指定的手机号码字符串
	 * @return 是否为手机号码格式:是为true，否则false
	 */
	public static Boolean isMobileNo(String str) {
		Boolean isMobileNo = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMobileNo;
	}

	/**
	 * 描述：是否只是字母和数字.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否只是字母和数字:是为true，否则false
	 */
	public static Boolean isNumberLetter(String str) {
		Boolean isNoLetter = false;
		String expr = "^[A-Za-z0-9]+$";
		if (str.matches(expr)) {
			isNoLetter = true;
		}
		return isNoLetter;
	}

	/**
	 * 描述：是否只是数字.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否只是数字:是为true，否则false
	 */
	public static Boolean isNumber(String str) {
		Boolean isNumber = false;
		String expr = "^[0-9]+$";
		if (str.matches(expr)) {
			isNumber = true;
		}
		return isNumber;
	}

	/**
	 * 描述：是否是邮箱.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否是邮箱:是为true，否则false
	 */
	public static Boolean isEmail(String str) {
		Boolean isEmail = false;
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 描述：是否是中文.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否是中文:是为true，否则false
	 */
	public static Boolean isChinese(String str) {
		Boolean isChinese = true;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
				} else {
					isChinese = false;
				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：是否包含中文.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否包含中文:是为true，否则false
	 */
	public static Boolean isContainChinese(String str) {
		Boolean isChinese = false;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					isChinese = true;
				} else {

				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：从输入流中获得String.
	 * 
	 * @param is
	 *            输入流
	 * @return 获得的String
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 最后一个\n删除
			if (sb.indexOf("\n") != -1
					&& sb.lastIndexOf("\n") == sb.length() - 1) {
				sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 描述：标准化日期时间类型的数据，不足两位的补0.
	 * 
	 * @param dateTime
	 *            预格式的时间字符串，如:2012-3-2 12:2:20
	 * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
	 */
	public static String dateTimeFormat(String dateTime) {
		StringBuilder sb = new StringBuilder();
		try {
			if (isEmpty(dateTime)) {
				return null;
			}
			String[] dateAndTime = dateTime.split(" ");
			if (dateAndTime.length > 0) {
				for (String str : dateAndTime) {
					if (str.indexOf("-") != -1) {
						String[] date = str.split("-");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append("-");
							}
						}
					} else if (str.indexOf(":") != -1) {
						sb.append(" ");
						String[] date = str.split(":");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append(":");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * 描述：不足2个字符的在前面补“0”.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 至少2个字符的字符串
	 */
	public static String strFormat2(String str) {
		try {
			if (str.length() <= 1) {
				str = "0" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 描述：截取字符串到指定字节长度.
	 * 
	 * @param str
	 *            the str
	 * @param length
	 *            指定字节长度
	 * @return 截取后的字符串
	 */
	public static String cutString(String str, int length) {
		return cutString(str, length, "");
	}

	/**
	 * 描述：截取字符串到指定字节长度.
	 * 
	 * @param str
	 *            文本
	 * @param length
	 *            字节长度
	 * @param dot
	 *            省略符号
	 * @return 截取后的字符串
	 */
	public static String cutString(String str, int length, String dot) {
		int strBLen = strlen(str, "GBK");
		if (strBLen <= length) {
			return str;
		}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = str.toCharArray();
		for (char c : ch) {
			sb.append(c);
			if (c > 256) {
				temp += 2;
			} else {
				temp += 1;
			}
			if (temp >= length) {
				if (dot != null) {
					sb.append(dot);
				}
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 描述：截取字符串从第一个指定字符.
	 * 
	 * @param str1
	 *            原文本
	 * @param str2
	 *            指定字符
	 * @param offset
	 *            偏移的索引
	 * @return 截取后的字符串
	 */
	public static String cutStringFromChar(String str1, String str2, int offset) {
		if (isEmpty(str1)) {
			return "";
		}
		int start = str1.indexOf(str2);
		if (start != -1) {
			if (str1.length() > start + offset) {
				return str1.substring(start + offset);
			}
		}
		return "";
	}

	/**
	 * 描述：获取字节长度.
	 * 
	 * @param str
	 *            文本
	 * @param charset
	 *            字符集（GBK）
	 * @return the int
	 */
	public static int strlen(String str, String charset) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = str.getBytes(charset).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 获取大小
	 */
	public static String getSize(long size) {
		StringBuilder sb = new StringBuilder();
		if (size >= UNIT_MB) {
			long mb = size >> 20;
			sb.append(mb);
			long leftSize = size & 0xFFFFF;
			leftSize = leftSize * 100 / UNIT_MB;
			if (leftSize != 0) {
				sb.append('.');
				if (leftSize < 10) {
					sb.append('0');
				}
				sb.append(leftSize);
			}
			sb.append("MB");
		} else if (size >= UNIT_KB) {
			long kb = size >> 10;
			sb.append(kb);
			long leftSize = size & 0x3FF;
			leftSize = leftSize * 100 / UNIT_KB;
			if (leftSize != 0) {
				sb.append('.');
				if (leftSize < 10) {
					sb.append('0');
				}
				sb.append(leftSize).append("KB");
			}
		} else {
			sb.append(size).append('B');
		}
		return sb.toString();
	}

	private static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat(
			"0.#");

	public static String getPrencet(long currSize, long totalSize) {
		double rate = 0;
		if (currSize <= 0 || totalSize <= 0) {
			rate = 0;
		} else if (currSize > totalSize) {
			rate = 100;
		} else {
			rate = (((double) currSize / totalSize) * 100);
		}
		return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format(rate))
				.append("%").toString();
	}

	/**
	 * 描述：ip地址转换为10进制数.
	 * 
	 * @param ip
	 *            the ip
	 * @return the long
	 */
	public static long ip2int(String ip) {
		ip = ip.replace(".", ",");
		String[] items = ip.split(",");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16
				| Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
	}
	public static String convertStr(int num){
		if(num>=0&&num<9){
			return "000"+num;
		}else if(num>=10&&num<99){
			return "00"+num;
		}else if(num>=100&&num<999){
			return "0"+num;
		}else {
			return ""+num;
		}
	}

}
