package com.baidu.translate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.shizh.common.util.FileUtil;
import me.shizh.common.util.MD5Util;
import me.shizh.common.util.RandomUtil;
import me.shizh.common.util.StringUtil;

//
//POST：
//http://fanyi.baidu.com/v2transapi
//
//Header:
//Host: fanyi.baidu.com
//User-Agent: Mozilla/5.0 (Windows NT 10.0; rv:45.0) Gecko/20100101 Firefox/45.0
//Accept: */*
//Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
//Accept-Encoding: gzip, deflate
//DNT: 1
//Content-Type: application/x-www-form-urlencoded; charset=UTF-8
//X-Requested-With: XMLHttpRequest
//Referer: http://fanyi.baidu.com/
//Content-Length: 63
//Cookie: BAIDUID=D45D142BB4AC830FBB4C3E6A0FEBC1AF:FG=1; BIDUPSID=D45D142BB4AC830FBB4C3E6A0FEBC1AF; PSTM=1455962681; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1458470560,1460800389,1460903460; to_lang_often=%5B%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22cht%22%2C%22text%22%3A%22%u4E2D%u6587%u7E41%u4F53%22%7D%5D; from_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22dan%22%2C%22text%22%3A%22%u4E39%u9EA6%u8BED%22%7D%2C%7B%22value%22%3A%22cht%22%2C%22text%22%3A%22%u4E2D%u6587%u7E41%u4F53%22%7D%5D; MCITY=-340%3A; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1460903801; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; H_PS_PSSID=1420_19671_19722_19690_15659_12153; __cfduid=d06f4dda8f45b2c0be80fce24f8ed59851460899080; locale=zh
//Connection: keep-alive
//
//Body：
//from=en&to=zh&query=gene&transtype=realtime&simple_means_flag=3

public class BaiduCiDian {

	private final static String httpUrl = "http://fanyi.baidu.com/v2transapi";

	private static HashMap<String, String> translate(String query, String from,
			String to) {
		HashMap<String, String> map = new HashMap<String, String>();

		URL url = null;
		PrintWriter out = null;
		HttpURLConnection conn;
		try {
			url = new URL(httpUrl);
			conn = (HttpURLConnection) url.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Referer", "http://fanyi.baidu.com/");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; rv:45.0) Gecko/20100101 Firefox/45.0");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print("from=" + from + "&to=" + to + "&query=" + query
					+ "&transtype=realtime&simple_means_flag=3");
			out.flush();// flush输出流的缓冲

			int rcode = conn.getResponseCode();
			if (200 == rcode) {
				String respStr = FileUtil.inputStream2String(conn.getInputStream());
				if(StringUtil.isNotNull(respStr)){
					map = createMap(map,from,respStr);
					
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		return map;
	}

	private static HashMap<String, String> createMap(
			HashMap<String, String> map, String from, String respStr) {
		JsonObject jo = new Gson().fromJson(respStr, JsonObject.class);
		
		//http://www.atool.org/chinese2unicode.php
		if("zh".equals(from)){
			map.put("trans_result", getStrFromJsonObject(jo,"trans_result.data#0.dst"));
			map.put("dict_simple", getStrFromJsonObject(jo,"dict_result.zdict.simple.means#0.exp#0.des#0.main"));
			map.put("dict_pinyin", getStrFromJsonObject(jo,"dict_result.zdict.simple.means#0.pinyin"));
			map.put("dict_detail", getStrFromJsonObject(jo,"dict_result.zdict.detail.means#0.exp#0.des#.main"));
			
			map.put("dict_detail", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.example#"));
			map.put("dict_similar_word", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.similar_word#"));
			map.put("dict_similar_word", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.similar_word#"));
			map.put("spd", "6");
			map.put("pit", "4");
			map.put("vol", "7");
			map.put("per", "1");
		}else{
			map.put("trans_result", getStrFromJsonObject(jo,"trans_result.data#0.dst"));
			map.put("dict_result", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.tr#"));
			map.put("dict_example", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.example#"));
			map.put("dict_similar_word", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.similar_word#"));
			map.put("dict_similar_word", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.similar_word#"));
		}
		
		
		return map;
	}

	public static void main(String[] args) {
		String text = "音量";
		String from = "zh";
		String to = "en";
		Map<String, String> map = translate(text, from, to);
		System.out.println(map.get(""));

	}

	/**
	 * eg：System.out.println(getStrFromJsonObject(jo,"edict.item#0.tr_group#0.tr#"));
	 * @param jo
	 * @param key
	 * @return
	 */
	private static String getStrFromJsonObject(JsonObject jo, String key) {
		JsonObject tmp = jo;
		String result = "";
		for(String k : key.split("[.]")){
			if(k.contains("#")){
				String []kk = k.split("[#]");
				if(kk.length==1){
					JsonArray ja = tmp.get(kk[0]).getAsJsonArray();
					for(int i=0;i<ja.size();i++){
						result += ja.get(i).getAsString()+"\n";
					}
				}else{
					tmp = tmp.get(kk[0]).getAsJsonArray().get(Integer.parseInt(kk[1])).getAsJsonObject();
				}
			}else{
				tmp = tmp.get(k).getAsJsonObject();
			}
		}
		if(StringUtil.isNull(result)){
			result = tmp.getAsString();
		}
		
		return result;
	}

}