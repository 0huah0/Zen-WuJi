package com.baidu.tts;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import me.shizh.common.util.PathUtil;
import me.shizh.common.util.StringUtil;
import me.shizh.common.util.UuidUtil;

/**
 * http://blog.csdn.net/iijse/article/details/6201101
 * 引入一个线程解决发音重叠的问题
 * @author Administrator
 * 
 */
public class BaiduTts extends Thread {
	private static String token = "24.e2059706aa491b187d42dc207dc395ad.2592000.1463130618.282335-7997390";
	private static String client_id = "AzzpGs1zEnGDKj3pw6s1OO4j";
	private static String client_secret = "9ba3602f82f3c1807b1b0ee9647fa925";
	private static int failRetry = 3;
	
	private static Player player = null;
	private static Thread t = null;
	
	//发音方式；顺序（一句一句来，用户需要等待），打断（终止上一句说下一句），重叠（前后两句一起，项两个人同时在说）
	
	private static void playMp3(int priority,InputStream in) {
		
		if(t != null){
			player.close();	//结束后线程会退出
		}
		
		try {
			player = new Player(new BufferedInputStream(in));
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		
		if(priority == 0){
			t = new BaiduTts();	//开始新线程
			t.start();
		}else{
			try {
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}
	}

	private static void baiduTtsApi(HashMap<String, String> map,int priority,String saveFile) {
		String httpUrl = "http://tsn.baidu.com/text2audio";
		
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

			String paramStr = "ctp=1&tok=" + token;
			for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
				String key = it.next();
				paramStr += ("&" + key+"="+map.get(key));
			}

			out.print(paramStr); // 发送请求参数
			out.flush();// flush输出流的缓冲

			// conn.connect();

			int rcode = conn.getResponseCode();
			if (502 == rcode) {
				token = BaiduOAuth.getToken(client_id, client_secret);
				// retry
				if (failRetry > 0) {
					failRetry--;
					baiduTtsApi(map,priority,saveFile);
				} else {
					failRetry = 3;
				}
			} else if (200 == rcode) {
				InputStream in = conn.getInputStream();
				if(StringUtil.isNotNull(saveFile)){
					fileSave(in,saveFile);	//保存MP3
				}
				playMp3(priority,in);	//播放
			} else {
				playMp3(9,new FileInputStream(PathUtil.getResourcesPath()+"resource/system/voice/系统提示，联网失败.mp3"));
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static String createMp3Filename(String text) {
		text = PathUtil.getResourcesPath()+"tmp/system/voice/"+UuidUtil.get32UUID()+".mp3";
		System.out.println(text);
		return text;
	}
	
	private static void fileSave(InputStream is, String saveFile) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(saveFile);
			byte[] b = new byte[1024];
			while ((is.read(b)) != -1) {
				fos.write(b);
			}
//			is.close();	//不要关闭
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void text2voice(String text, HashMap<String, String> params) {
		if ("".equals(token)) {
			token = BaiduOAuth.getToken(client_id, client_secret);
		}
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
		
		baiduTtsApi(map,getVoicePlayPriority(text),null);
	}
	
	/**
	 * 确定播放优先级，==0可以被中断
	 * 以下情况可以被中断：
	 * 		1.如果正在播放的语句，是简单问答，比较长（如预计在10秒以上）；
	 * 		2.或者，如果正在播放的语句不是非得听完的（如系统提示、说明、解释）
	 * @param text 
	 * @return
	 */
	private static int getVoicePlayPriority(String text) {
		int priority = 0;
		if(StringUtil.isNotNull(text) && text.matches("^(系统提示|警告).*")){
			priority = 9;
		}else{
			
		}
		
		return priority;
	}

	public static String voice2text(String input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void run() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			System.out.println(e.getMessage());
		}
		t = null;
	}

	public static void main(String[] args) {
		
		String text = "电脑每次开机都要输入这个，要不然就一直是黑屏，没有桌面显示";
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
		

		baiduTtsApi(map,0,null);
//		String savefile = createMp3Filename(text);
//		baiduTtsApi(map,savefile);
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
		System.out.println("done!");
	}

	
	
	
}