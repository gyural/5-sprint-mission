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
	// Foreign key
	private final UUID authorId;
	private final UUID channelId;
	private final List<UUID> attachmentIds;

	public Message(String content, @NonNull UUID authorId, @NonNull UUID channelId) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.attachmentIds = new ArrayList<>();
	}

	public Message(String content, @NonNull UUID authorId, @NonNull UUID channelId, List<UUID> attachmentIds) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.attachmentIds = attachmentIds != null ? new ArrayList<>(attachmentIds) : new ArrayList<>();
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
		  '}';
	}

}
