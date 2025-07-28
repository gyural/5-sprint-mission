package com.sprint.mission.discodeit.entity;

import java.util.Objects;
import java.util.UUID;

public class Message {
	private final UUID id;
	private final Long createdAt;
	private Long updatedAt;
	private String content;
	private UUID authorId; // Optinal
	private UUID channelId;
	private String authorName; // 유저가 채널을 나가도 메시지의 작성자는 남아있어야 하므로, authorId와 authorName을 분리

	public Message(String content, UUID authorId, UUID channelId, String authorName) {
		this.id = UUID.randomUUID();
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = null;
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.updatedAt = System.currentTimeMillis();
	}

	public UUID getAuthorId() {
		return authorId;
	}

	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
		this.updatedAt = System.currentTimeMillis();
	}

	public UUID getChannelId() {
		return channelId;
	}

	public UUID getId() {
		return id;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public String getAuthorName() {
		return authorName;
	}

	@Override
	public String toString() {
		return "Message{" +
		  "id=" + id +
		  ", createdAt=" + createdAt +
		  ", updatedAt=" + updatedAt +
		  ", content='" + content + '\'' +
		  ", authorId=" + authorId +
		  ", channelId=" + channelId +
		  ", authorName='" + authorName + '\'' +
		  '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Message message = (Message)o;
		return Objects.equals(id, message.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
