package me.shizh.ai.zen.descion;

import java.util.List;

public class Categories {

	private String input_pattern;
	private String input_regular;

	private String output_default;
	private List<String> output_random;
	private String output_md; // machine_decision

	public String getInput_pattern() {
		return input_pattern;
	}

	public void setInput_pattern(String input_pattern) {
		this.input_pattern = input_pattern;
	}

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

}
