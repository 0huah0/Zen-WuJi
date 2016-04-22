package me.shizh.ai.zen.features.text;

/**
 * word varchar(100) primary key, lang varchar(20), trans_result varchar(500),
 * dict_simple varchar(500), dict_detail varchar(1000), dict_pinyin
 * varchar(500), baike_link varchar(1000), baike_image varchar(1000),
 * baike_content varchar(3000)
 */
public class Word {

	private String word;
	private String lang;
	private String trans_result;
	private String dict_simple;
	private String dict_detail;
	private String dict_pinyin;
	private String baike_link;
	private String baike_image;
	private String baike_content;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTrans_result() {
		return trans_result;
	}

	public void setTrans_result(String trans_result) {
		this.trans_result = trans_result;
	}

	public String getDict_simple() {
		return dict_simple;
	}

	public void setDict_simple(String dict_simple) {
		this.dict_simple = dict_simple;
	}

	public String getDict_detail() {
		return dict_detail;
	}

	public void setDict_detail(String dict_detail) {
		this.dict_detail = dict_detail;
	}

	public String getDict_pinyin() {
		return dict_pinyin;
	}

	public void setDict_pinyin(String dict_pinyin) {
		this.dict_pinyin = dict_pinyin;
	}

	public String getBaike_link() {
		return baike_link;
	}

	public void setBaike_link(String baike_link) {
		this.baike_link = baike_link;
	}

	public String getBaike_image() {
		return baike_image;
	}

	public void setBaike_image(String baike_image) {
		this.baike_image = baike_image;
	}

	public String getBaike_content() {
		return baike_content;
	}

	public void setBaike_content(String baike_content) {
		this.baike_content = baike_content;
	}

}
