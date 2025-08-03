package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common {
	private String content;
	private UUID authorId; // Assuming this is a UUID in string format
	private UUID channelId; // Assuming this is a UUID in string format

	public Message(String content, UUID authorId, UUID channelId) {
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

	public UUID getAuthorId() {
		return authorId;
	}

	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	public UUID getChannelId() {
		return channelId;
	}

	public void setChannelId(UUID channelId) {
		this.channelId = channelId;
	}
}
