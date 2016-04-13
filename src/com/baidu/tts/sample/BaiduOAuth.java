package com.baidu.tts.sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class BaiduOAuth {
	public static String getToken(String client_id, String client_secret) {
		StringBuffer sb = new StringBuffer();
		String httpUrl = "https://openapi.baidu.com/oauth/2.0/token?";

		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		try {
			PrintWriter out = null;
			URLConnection conn = url.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			conn.setDoOutput(true);
			conn.setDoInput(true);

			String param = "grant_type=client_credentials" + "&client_id="
					+ client_id + "&client_secret=" + client_secret;

			out = new PrintWriter(conn.getOutputStream());
			out.print(param); // 发送请求参数
			out.flush();// flush输出流的缓冲

			BufferedReader breader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			while ((str = breader.readLine()) != null) {
				sb.append(str);
			}
			breader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String []args) {
		String client_id = "AzzpGs1zEnGDKj3pw6s1OO4j";
		String client_secret = "9ba3602f82f3c1807b1b0ee9647fa925";
		String respStr = getToken(client_id, client_secret);
		//{"access_token":"24.e2059706aa491b187d42dc207dc395ad.2592000.1463130618.282335-7997390","session_key":"9mzdXveMyG4C1K6u2gtibmD5PFPzRtOjHKHPMmrtzXWFNHsu\/Ex9ULvtzdFMtFwg6PTdWGflNqH7W1PRLtQQTyE+RpOI","scope":"public audio_voice_assistant_get audio_tts_post wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian","refresh_token":"25.dbb9965a6e69f2cecffef5c1d9a3acf3.315360000.1775898618.282335-7997390","session_secret":"6ca063dd5df89cf40fe7f407fbf8a85b","expires_in":2592000}
		System.out.println(respStr);  
		JSONObject jsonobject = new JSONObject(respStr);
		String access_token = (String) jsonobject.get("access_token");
		System.out.println(access_token);

	}
}