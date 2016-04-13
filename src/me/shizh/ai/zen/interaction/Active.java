package me.shizh.ai.zen.interaction;

public class Active {
	private int try_times = 3;
	private int start_from_last_talk = 10; // second
	private String[] random_topic;
	private String topic_choice_md; // md

	public int getTry_times() {
		return try_times;
	}

	public void setTry_times(int try_times) {
		this.try_times = try_times;
	}

	public int getStart_from_last_talk() {
		return start_from_last_talk;
	}

	public void setStart_from_last_talk(int start_from_last_talk) {
		this.start_from_last_talk = start_from_last_talk;
	}

	public String[] getRandom_topic() {
		return random_topic;
	}

	public void setRandom_topic(String[] random_topic) {
		this.random_topic = random_topic;
	}

	public String getTopic_choice_md() {
		return topic_choice_md;
	}

	public void setTopic_choice_md(String topic_choice_md) {
		this.topic_choice_md = topic_choice_md;
	}

	
}
