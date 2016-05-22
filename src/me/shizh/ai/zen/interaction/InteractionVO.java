package me.shizh.ai.zen.interaction;

import java.util.List;

public class InteractionVO {

	private RespTimeDelay resp_time_delay;
	private ActiveVO activeVO; // 主动

	private String last_io_point;
	private Integer last_io_point_expire = 10; // seconds //上次谈话点过期时间

	private List<String[]> history; // [互动时间点,交互类型,内容]

	/**
	 * 会话记忆栈（String二维数组["问题","预期答案1|预期答案2|预期答案3|"]）：
	 * 类型：
	 * 1.确认，记录向用户提出的问题，如“你确定吗？”、“你的意思是xxx吗？”，用于响应对用户的确认结果，如"是"、“不是”、“对”、“也许”、“好”、“嗯”这种
	 * 2.
	 */
	private List<String[]> memoryStack; //会话记忆栈
	
	public RespTimeDelay getResp_time_delay() {
		return resp_time_delay;
	}

	public void setResp_time_delay(RespTimeDelay resp_time_delay) {
		this.resp_time_delay = resp_time_delay;
	}

	public ActiveVO getActiveVO() {
		return activeVO;
	}

	public void setActiveVO(ActiveVO activeVO) {
		this.activeVO = activeVO;
	}

	public String getLast_io_point() {
		return last_io_point;
	}

	public void setLast_io_point(String last_io_point) {
		this.last_io_point = last_io_point;
	}

	public Integer getLast_io_point_expire() {
		return last_io_point_expire;
	}

	public void setLast_io_point_expire(Integer last_io_point_expire) {
		this.last_io_point_expire = last_io_point_expire;
	}

	public List<String[]> getHistory() {
		return history;
	}

	public void setHistory(List<String[]> history) {
		this.history = history;
	}

	public List<String[]> getMemoryStack() {
		return memoryStack;
	}

	public void setMemoryStack(List<String[]> memoryStack) {
		this.memoryStack = memoryStack;
	}

}
