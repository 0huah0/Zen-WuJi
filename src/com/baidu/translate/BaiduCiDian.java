package com.baidu.translate;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shizh.common.util.FileUtil;
import me.shizh.common.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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

	private static HashMap<String, String> translate(String query, String from, String to) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("word", query);
		map.put("lang", from);
		
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
					fillMap(map,from,respStr);
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	private static HashMap<String, String> fillMap(
			HashMap<String, String> map, String from, String respStr) {
		JsonObject jo = new Gson().fromJson(respStr, JsonObject.class);
		
		//http://www.atool.org/chinese2unicode.php
		map.put("trans_result",getStrFromJsonObject(jo,"trans_result.data#0.dst"));
		if("zh".equals(from)){
			map.put("dict_simple", getStrFromJsonObject(jo,"dict_result.zdict.simple.means#0.exp#0.des#0.main"));
			map.put("dict_detail", getStrFromJsonObject(jo,"dict_result.zdict.detail.means#0.exp#0.des#.main"));
			map.put("dict_pinyin", getStrFromJsonObject(jo,"dict_result.zdict.simple.means#0.pinyin"));
		}else{
			map.put("dict_result",  getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.tr#"));
			map.put("dict_example", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.example#"));
			map.put("dict_similar", getStrFromJsonObject(jo,"dict_result.edict.item#0.tr_group#0.similar_word#"));
		}
		
		map.put("baike_content",getStrFromJsonObject(jo,"dict_result.baike_means.content"));
		map.put("baike_image",	getStrFromJsonObject(jo,"dict_result.baike_means.image"));
		map.put("baike_link",	getStrFromJsonObject(jo,"dict_result.baike_means.link"));
		
		return map;
	}


	/**
	 * eg：System.out.println(getStrFromJsonObject(jo,"edict.item#0.tr_group#0.tr#"));
	 * eg:item#2表示取数组item的第二是元素值
	 * @param jo
	 * @param key
	 * @return
	 */
	private static String getStrFromJsonObject(JsonObject jo, String key) {
		System.out.println(key);
		JsonObject tmp = jo;
		String result = "";
		String []keys = key.split("[.]");
		for(int i=0;i<keys.length;i++){
			if(keys[i].contains("#")){ //dict_result.edict.item#0.tr_group#0.tr#
				String []kk = keys[i].split("[#]");
				if(kk.length==1){
					if(StringUtil.isNull(tmp.get(kk[0]))){
						break;	//!!!
					}
					JsonArray ja = tmp.get(kk[0]).getAsJsonArray();
					for(int j=0;j<ja.size();j++){
						if(ja.get(j) instanceof JsonObject){
							String str = key.substring(key.lastIndexOf(".")+1);
							result += getStrFromJsonObject(ja.get(j).getAsJsonObject(),str);
						}else{
							result += ja.get(j).getAsString()+"\n";
						}
					}
					break;
				}else{
					if(tmp.get(kk[0]) != null){
						tmp = tmp.get(kk[0]).getAsJsonArray().get(Integer.parseInt(kk[1])).getAsJsonObject();
					}
				}
			}else{	
				if(StringUtil.isNull(tmp.get(keys[i]))){
					break;	//!!!
				}
				if(tmp.get(keys[i]) instanceof JsonObject){
					tmp = tmp.get(keys[i]).getAsJsonObject();
				}else if(tmp.get(keys[i]) instanceof List){
					
				}else if(tmp.get(keys[i]) instanceof JsonPrimitive){
					result = tmp.get(keys[i]).getAsJsonPrimitive().getAsString();
				}
			}
			if(i == keys.length){
				result = tmp.getAsString();
			}
		}
		
		return result;
	}

	public static HashMap<String, String> query(String word){
		boolean isZh = word.matches("^[\u4e00-\u9fa5]{2,}$"); //汉字
		return translate(word,!isZh?"en":"zh",isZh?"en":"zh");
	}

	public static void main(String[] args) {
		String text = "量子力学";
		String from = "zh";
		String to = "en";
		Map<String, String> map = translate(text, from, to);
		System.out.println(map.get(""));

	}
	
}