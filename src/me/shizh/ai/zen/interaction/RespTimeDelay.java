package me.shizh.ai.zen.interaction;

import java.util.List;

public class RespTimeDelay {
	private List<String> _default;
	private String md; // //machine_decision 用java类决策，考虑对话上下文

	public List<String> get_default() {
		return _default;
	}

	public void set_default(List<String> _default) {
		this._default = _default;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

}
