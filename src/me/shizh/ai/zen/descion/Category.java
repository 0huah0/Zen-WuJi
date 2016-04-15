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
import com.google.gson.reflect.TypeToken;

public class Category {
	
	private final static String category = PathUtil.getResourcesPath()+"data/categories/"; 
	
	private String input_regular;

	private String output_default;
	private List<String> output_random;
	private String output_md; // machine_decision

	public String getInput_regular() {
		return input_regular;
	}

	public void setInput_regular(String input_regular) {
		this.input_regular = input_regular;
	}

	public String getOutput_default() {
		return output_default;
	}

	public void setOutput_default(String output_default) {
		this.output_default = output_default;
	}

	public List<String> getOutput_random() {
		return output_random;
	}

	public void setOutput_random(List<String> output_random) {
		this.output_random = output_random;
	}

	public String getOutput_md() {
		return output_md;
	}

	public void setOutput_md(String output_md) {
		this.output_md = output_md;
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
					categories.put(key, (List<Category>) gson.fromJson(str, type));
				}
			}
		}
		return categories;
	}

	public static void saveAll(Map<String, List<Category>> categories) {
		// TODO Auto-generated method stub
		
	}

}
