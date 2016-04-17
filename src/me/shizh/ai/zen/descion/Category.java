package me.shizh.ai.zen.descion;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shizh.common.util.FileUtil;
import me.shizh.common.util.PathUtil;
import me.shizh.common.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Category {
	
	private final static String category = PathUtil.getResourcesPath()+"data/categories/"; 
	
	private String _regular;

	private String default_;
	private List<String> random_;
	private String md_; // machine_decision

	public String get_regular() {
		return _regular;
	}

	public void set_regular(String _regular) {
		this._regular = _regular;
	}

	public String getDefault_() {
		return default_;
	}

	public void setDefault_(String default_) {
		this.default_ = default_;
	}

	public List<String> getRandom_() {
		return random_;
	}

	public void setRandom_(List<String> random_) {
		this.random_ = random_;
	}

	public String getMd_() {
		return md_;
	}

	public void setMd_(String md_) {
		this.md_ = md_;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, List<Category>> loadCategories() {
		Map<String, List<Category>> categories = new HashMap<String, List<Category>>();
		
		File dir = new File(category);
		if(dir.isDirectory()){
			Gson gson = new Gson();
			for(File jf :dir.listFiles()){
				String str = null;
				try {
					str = new String(FileUtil.getContent(jf.getAbsolutePath()),"utf-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(StringUtil.isNotNull(str)){
					//http://blog.csdn.net/luhuajcdd/article/details/7971271
					Type type = new TypeToken<ArrayList<Category>>(){}.getType();
					String key = jf.getName().substring(0, jf.getName().lastIndexOf("."));
					try{
						categories.put(key, (List<Category>) gson.fromJson(str, type));
					}catch(JsonSyntaxException e){
						System.out.println(e.getMessage());
					}
				}
			}
		}
		return categories;
	}

	public static void saveAll(Map<String, List<Category>> categories) {
		// TODO Auto-generated method stub
		
	}

}
