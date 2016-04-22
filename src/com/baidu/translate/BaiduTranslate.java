package com.baidu.translate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import me.shizh.common.util.MD5Util;
import me.shizh.common.util.RandomUtil;

/**
 * @author Administrator
 */
public class BaiduTranslate {
	private static String token = "24.e2059706aa491b187d42dc207dc395ad.2592000.1463130618.282335-7997390";
	private static String client_id = "AzzpGs1zEnGDKj3pw6s1OO4j";
	private static String client_secret = "9ba3602f82f3c1807b1b0ee9647fa925";

	private final static String httpUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate";

	private static HashMap<String, String> baiduTranslateApi(HashMap<String, String> map) {
		URL url = null;
		try {
			url = new URL(httpUrl);
			PrintWriter out = null;
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			// conn.setRequestProperty("Content-Type", "audio/mp3");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());
			
			int salt = RandomUtil.random(0, Integer.MAX_VALUE);
			String sign = MD5Util.md5(map.get("appid")+salt+map.get("q"));	//appid+q+salt+密钥 的MD5值
			String paramStr = "salt=" + salt;
			for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
				String key = it.next();
				paramStr += ("&" + key+"="+map.get(key));
			}

			out.print(paramStr); // 发送请求参数
			out.flush();// flush输出流的缓冲

			// conn.connect();

			int rcode = conn.getResponseCode();
			if (200 == rcode) {
				InputStream in = conn.getInputStream();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}


	public static void main(String[] args) {
		
	}

	
	
	
}