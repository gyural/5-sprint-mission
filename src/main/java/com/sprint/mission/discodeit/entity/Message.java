package com.sprint.mission.discodeit.entity;

public class Message extends Common {
	private String content;
	private String authorId; // Assuming this is a UUID in string format
	private String channelId; // Assuming this is a UUID in string format

	public Message(String content, String authorId, String channelId) {
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
