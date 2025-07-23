package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message extends Common implements Serializable {
	private static final long serialVersionUID = 1L;

	private String content;
	private UUID authorId; // Optinal
	private UUID channelId;
	private String authorName; // 유저가 채널을 나가도 메시지의 작성자는 남아있어야 하므로, authorId와 authorName을 분리

	public Message(String content, UUID authorId, UUID channelId, String authorName) {
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.authorName = authorName;
	}

	public Message(String content, UUID channelId, UUID authorId) {
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.authorName = null;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" +
		  "Message{" +
		  "content='" + content + '\'' +
		  ", authorId=" + authorId +
		  ", channelId=" + channelId +
		  ", authorName='" + authorName + '\'' +
		  '}';
	}
}
