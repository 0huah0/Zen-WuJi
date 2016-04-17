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


	public static void text2voice(String text, HashMap<String, String> params) {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put( "tex", text );	//!!!
		map.put( "cuid", "42dc207dc395ad" );
		map.put( "lan", "zh" );
		map.put( "spd", "6" );
		map.put( "pit", "4" );
		map.put( "vol", "7" );
		map.put( "per", "1" );
		
		//合并参数
		for(Iterator<String> it = params.keySet().iterator();it.hasNext();){
			String key = it.next();
			map.put(key, params.get(key));
		}
		
	}
	
	public static String voice2text(String input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
		String text = "系统提示，联网失败。";
		/*
		 * spd 选填 语速，取值 0-9，默认为 5 
		 * pit 选填 音调，取值 0-9，默认为 5 
		 * vol 选填 音量，取值 0-9，默认为 5
		 * per 选填 发音人选择，取值 0-1 ；0 为女声，1 为男声，默认为女声
		 */
		HashMap<String, String> map = new HashMap<String,String>();
		map.put( "tex", text );
		map.put( "cuid", "42dc207dc395ad" );
		map.put( "lan", "zh" );
		map.put( "spd", "6" );
		map.put( "pit", "4" );
		map.put( "vol", "7" );
		map.put( "per", "1" );
		
		
		System.out.println("done!");
	}

	
	
	
}