package me.shizh.ai.zen.features.text;

import java.util.HashMap;
import java.util.List;

import me.shizh.db.derby.DerbyDao;

import com.baidu.translate.BaiduCiDian;

/**
 * 文本语言能力
 * 
 */
public class Text {

	public static String[] queryWord(String word) {
		List<String[]> records = DerbyDao.query("from word_dict where word=?",
				word);
		if (records == null || records.size() == 0) {
			HashMap<String, String> map = BaiduCiDian.query(word);
			records.add(new String[] { map.get("word"), map.get("lang"),
					map.get("trans_result"), map.get("dict_simple"),
					map.get("dict_detail"), map.get("dict_pinyin"),
					map.get("baike_link"), map.get("baike_image"),
					map.get("baike_content") });

			DerbyDao.saft_insert("insert into word_dict values(?,?,?,?,?,?,?,?,?)",
					records.get(0));
		}


		return records.get(0);
	}

	public static void main(String[] args) {
		String[] attr = queryWord("基因");
		for (String str : attr) {
			System.out.println(str);
		}
	}
}
