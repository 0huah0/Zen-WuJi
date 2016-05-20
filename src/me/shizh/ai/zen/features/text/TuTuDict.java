package me.shizh.ai.zen.features.text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import me.shizh.common.util.PathUtil;

public class TuTuDict {

	private String find(String text) {
		if (text.length() == 0){
			return "";
		}
		
		int i = 1;
		String str = text.toLowerCase();
		for (int k = 0; k < str.length(); k++)
			if ((str.charAt(0) < 'a') || (str.charAt(0) > 'z')) {
				i = 0;
				break;
			}
		if (i == 0)return "-1对不起，本字典只能查询英文单词。";
		
		
		InputStreamReader isr = null;
		for (int m = Math.min(3, str.length()); m > 1; m--){
			String file = PathUtil.getResourcesPath()+ "data/tutu_dict_data/" + str.substring(0, m)+ "_";
			try {
				isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
			}
			if(isr != null){
				break;
			}
		}
		
		int n = 10;
		int i1 = -1;
		int i2 = 0;
		StringBuffer sb = new StringBuffer();
		try {
			i1 = isr.read();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		while (i1 != -1){
			if (i1 == 10) {
				i2 = 1;
				for (int i3 = 0; i3 < str.length(); i3++) {
					try {
						i1 = isr.read();
					} catch (IOException e) {
						e.printStackTrace();
						return e.getMessage();
					}
					if (str.charAt(i3) != i1) {
						if (str.charAt(i3) < i1) {
							n = 0;
							i2 = 0;
							break;
						}
						i2 = 0;
						break;
					}
				}
				if (i2 != 0) {
					StringBuffer sb2 = new StringBuffer();
					while ((i1 != -1) && (i1 != 10)) {
						try {
							i1 = isr.read();
						} catch (IOException e) {
							e.printStackTrace();
							return e.getMessage();
						}
						sb2.append((char) i1);
					}
					sb.append(str);
					sb.append(sb2.toString());
					n--;
				}
				if (n <= 0)
					break;
			} else {
				try {
					i1 = isr.read();
				} catch (IOException e) {
					e.printStackTrace();
					return e.getMessage();
				}
			}
		}
		try {
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (sb.length() > 0){
			return sb.toString();
		}
		return "-1对不起，没有找到单词的匹配解释。";
	}

	public static void main(String[] args) {
		System.out.println(new TuTuDict().find("exception"));

	}

}