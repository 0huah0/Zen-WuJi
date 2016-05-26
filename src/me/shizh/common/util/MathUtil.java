package me.shizh.common.util;

import java.util.HashMap;
import java.util.Map;

import me.shizh.common.math.MathCalculator;

/**
 * 字符串相关方法
 * 
 */
public class MathUtil {

	public static final String[] units = { "十", "百", "千", "万", "亿" };
	public static final String[] numArrayZh = { "零", "一", "二", "三", "四", "五",
			"六", "七", "八", "九" };
	public static final String[] numArray = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9" };
	public static final String[][] opers = new String[][] { { "-", "-" },
			{ "减", "-" }, { "minus", "-" }, { "+", "+" }, { "加", "+" },
			{ "add", "+" }, { "plus", "+" }, { "and", "+" }, { "*", "*" },
			{ "×", "*" }, { "X", "*" }, { "x", "*" }, { "乘", "*" },
			{ "time", "*" }, { "multiply", "*" }, { "/", "/" }, { "\\", "/" },
			{ "÷", "/" }, { "除", "/" }, { "divide", "/" }, { "%", "%" },
			{ "余", "%" }, { "模", "%" }, { "^", "^" }, { "方", "^" },
			{ "~", "~" }, { "根", "~" } };

	/**
	 * 将input中文数量转化为阿拉伯数量 eg:"三亿零六百零七万零九百零六"->306070906
	 * 
	 * @param str中文数量
	 *            ，如“三千零五十万四千三百二十六亿零六百零七万一千九百零六”
	 */
	public static Long chineseToNum(String input) {
		Map<String, Long> units = new HashMap<String, Long>();
		units.put("亿", 100000000L);
		units.put("万", 10000L);
		units.put("千", 1000L);
		units.put("百", 100L);
		units.put("十", 10L);

		Map<String, Integer> nums = new HashMap<String, Integer>();
		String[] numStrs = new String[] { "零", "一", "二", "三", "四", "五", "六",
				"七", "八", "九" };
		for (int i = 0; i < numStrs.length; i++) {
			nums.put(numStrs[i], i);
		}
		nums.put("〇", 0);

		Long total = 0L;
		Long num = 0L;
		Integer tmp = 0;
		Long lastUnit = null;
		Long maxUnit = 0L;
		char[] ss = input.toCharArray();
		for (int i = 0; i < ss.length; i++) {
			Long v = units.get(String.valueOf(ss[i]));
			if (v == null) { // 是数值
				tmp = nums.get(String.valueOf(ss[i]));
				if (i == ss.length - 1) {
					total += num + tmp;
				}
			} else { // 是单位
				if (lastUnit != null && lastUnit < v) { // 比前一个单位大，如三百零五万，则乘
					if (v > maxUnit) {
						maxUnit = v;
						total = (total + num + tmp) * v;
					} else {
						total += (num + tmp) * v;
					}
					num = 0L;
				} else { // 比前一个单位小，如一千三百，则乘加
					if (v == 10 && i == 0) {
						num = v;
					} else {
						num += tmp * v;
					}
					tmp = 0;
				}
				lastUnit = v;
			}
		}

		return total == 0 ? num : total;
	}

	/**
	 * 将input中的中文数字转化为阿拉伯数字 eg:"三25百六十4"->"32564"
	 * 
	 * @param input
	 * @return
	 */
	public static String chineseNumFormat(String input) {
		Map<String, String> nums = new HashMap<String, String>();
		String[] numStrs = new String[] { "零", "一", "二", "三", "四", "五", "六",
				"七", "八", "九" };
		for (int i = 0; i < numStrs.length; i++) {
			nums.put(numStrs[i], "" + i);
		}
		nums.put("〇", "0");
		nums.put("点", ".");

		String aString = "";
		char[] ss = input.toCharArray();
		for (char s : ss) {
			String tmp = nums.get(String.valueOf(s));
			if (tmp == null) {
				if(!"十百千万亿".contains(String.valueOf(s))){
					aString += s;
				}
			} else {
				aString += tmp;
			}
		}
		System.out.println(aString);
		return aString;
	}

	/**
	 * 将input中的阿拉伯数字转化为中文数字 eg:"三25六4"->"三二五六四"
	 * 
	 * @param input
	 * @return
	 */
	public static String numChineseFormat(String input) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "零");
		map.put("1", "一");
		map.put("2", "二");
		map.put("3", "三");
		map.put("4", "四");
		map.put("5", "五");
		map.put("6", "六");
		map.put("7", "七");
		map.put("8", "八");
		map.put("9", "九");

		String aString = "";
		char[] ss = input.toCharArray();
		for (char s : ss) {
			String tmp = map.get(String.valueOf(s));
			if (tmp == null) {
				aString += s;
			} else {
				aString += tmp;
			}
		}
		return aString;
	}

	/*
	 * 从input抽取数学算式 eg:"12加5-四再乘以三四"->"12+5-4*34"
	 * System.out.println(chineseNumFormat("5-4*3十1"));
	 */
	public static String mathExprfmt(String input) {
		String output = "";
		// \d+运算符\d+ ,如果数字和数字之间有不含运算符的其他（即出现｛数字，忽略字符，数字｝），则中断
		int[] pattern = new int[] { 0, 0, 0 };// {1数字|2运算符和单位|3非法字符}
		for (int i = 0; i < input.length(); i++) {
			if(pattern[2] != 0){
				pattern[0] = pattern[1];
				pattern[1] = pattern[2];
				pattern[2] = 0;
			}

			String formd = null;
			String chr = input.substring(i, i + 1);
			for (String[] oper : opers) {
				if (oper[0].equals(chr)) {
					formd = oper[1];
					if(pattern[1]!=2){
						pattern[2] = 2;
					}
				}
			}
			if (formd == null) {
				for (String str : numArray) {
					if (str.equals(chr)) {
						formd = str;
						if(pattern[1]!=1){
							pattern[2] = 1;
						}
					}
				}
			}
			if (formd == null) {
				for (String str : numArrayZh) {
					if (str.equals(chr)) {
						formd = str;
						if(pattern[1]!=1){
							pattern[2] = 1;
						}
					}
				}
			}
			for (String str : units) {
				if (str.equals(chr)) {
					formd = str;
					if(pattern[1]!=2){
						pattern[2] = 2;
					}
				}
			}
			if("点.".contains(chr)){
				formd = ".";
				pattern[2] = 1;
			}
			if (formd == null) {
				if(pattern[1]!=3){
					pattern[2] = 3;
				}
			}else{
				if (pattern[0] == 1 && pattern[1] == 3 && pattern[2] == 1) {
					break;
				}
				output += formd;
			}
//			System.out.println(i+":\t"+pattern[0]+" "+pattern[1]+" "+pattern[2]);
		}

		return chineseNumFormat(output);
	}

	public static void main(String[] args) {

		try {
			String exp = mathExprfmt("5-去四点1再乘上三十一是不是9");
			System.out.println(exp);
			System.out.println(new MathCalculator().analysis(exp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
