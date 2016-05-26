package me.shizh.ai.zen.descion.md;

import java.util.List;

import me.shizh.common.math.MathCalculator;
import me.shizh.common.util.MathUtil;

/**
 * 处理数学计算
 * 
 * @author Administrator
 */
public class MdMathSimple implements MdBase {

	public String doMachineDecision(String input, List<String> inputs) {
		String output = "";
		if (output.matches("(?:根号|开方|开根)")) {
			output = "无法计算开方。";
		}
		try {
			output = new MathCalculator().analysis(MathUtil.mathExprfmt(input))
					.toString();
		} catch (Exception e) {
			output = "是数学计算吗？不能识别你的表达，请说得更清楚一点。";
		}
		return output;
	}

	// "(?:([\\+\\-\\*\\/加减乘除%模]?)([\\d一-九零〇十百千万亿]+))?(?:等于|=|是多少)?";
	public static void main(String[] args) {
		String input = "请计算三百四十五点1+11.2再加1加12/2减去2*3是多少";
		try {
			System.out.println(new MathCalculator().analysis(MathUtil
					.mathExprfmt(input)));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
