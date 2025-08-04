package com.sprint.mission.discodeit.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Message implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;
	private String content;
	private String authorName; // 유저가 채널을 나가도 메시지의 작성자는 남아있어야 하므로, authorId와 authorName을 분리
	// Foreign key
	private final UUID authorId;
	private final UUID channelId;
	private final List<UUID> attachmentIds;

	public Message(String content, UUID channelId, UUID authorId) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.attachmentIds = new ArrayList<>();
	}

	public Message(String content, @NonNull UUID authorId, @NonNull UUID channelId, String authorName) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.authorName = authorName;
		this.attachmentIds = new ArrayList<>();

	}

	public Instant getLastEditedAt() {
		return updatedAt != null ? updatedAt : createdAt;
	}

	public void setContent(String content) {
		this.content = content;
		this.updatedAt = Instant.now();
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

}
