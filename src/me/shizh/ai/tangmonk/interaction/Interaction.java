package me.shizh.ai.tangmonk.interaction;

import java.util.List;

public class Interaction {

	private RespTimeDelay resp_time_delay;
	private Active active; // 主动

	private String last_io_point;
	private Integer last_io_point_expire = 10; // seconds //上次谈话点过期时间

	private List<String[]> history; // [互动时间点,交互类型,内容]

	public RespTimeDelay getResp_time_delay() {
		return resp_time_delay;
	}

	public void setResp_time_delay(RespTimeDelay resp_time_delay) {
		this.resp_time_delay = resp_time_delay;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
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

}
