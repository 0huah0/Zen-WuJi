package me.shizh.ai.tangmonk.user;

import java.util.List;

public class UserType {
	private List<String> style;
	private List<String> interest;
	private List<String> tags;

	public List<String> getStyle() {
		return style;
	}

	public void setStyle(List<String> style) {
		this.style = style;
	}

	public List<String> getInterest() {
		return interest;
	}

	public void setInterest(List<String> interest) {
		this.interest = interest;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
