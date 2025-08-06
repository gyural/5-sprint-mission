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

	/**
	 * Constructs a new Message with the specified content, author, channel, and author name.
	 *
	 * Initializes the message with a unique identifier, sets the creation timestamp to the current instant, and creates an empty list for attachments.
	 *
	 * @param content     the textual content of the message
	 * @param authorId    the unique identifier of the message author
	 * @param channelId   the unique identifier of the channel to which the message belongs
	 * @param authorName  the name of the message author
	 */
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

	/**
	 * Constructs a new Message with the specified content, author information, channel, and attachments.
	 *
	 * @param content        the textual content of the message
	 * @param authorId       the unique identifier of the message author
	 * @param channelId      the unique identifier of the channel to which the message belongs
	 * @param authorName     the name of the message author, preserved for historical reference
	 * @param attachmentIds  a list of attachment UUIDs associated with the message; if null, an empty list is used
	 */
	public Message(String content, @NonNull UUID authorId, @NonNull UUID channelId, String authorName,
	  List<UUID> attachmentIds) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.content = content;
		this.authorId = authorId;
		this.channelId = channelId;
		this.authorName = authorName;
		this.attachmentIds = attachmentIds != null ? new ArrayList<>(attachmentIds) : new ArrayList<>();
	}

	/**
	 * Returns the timestamp of the last edit to the message.
	 *
	 * If the message has never been edited, returns the creation timestamp.
	 *
	 * @return the time the message was last edited, or the creation time if never edited
	 */
	public Instant getLastEditedAt() {
		return updatedAt != null ? updatedAt : createdAt;
	}

	/**
	 * Updates the message content and sets the last updated timestamp to the current time.
	 *
	 * @param content the new content for the message
	 */
	public void setContent(String content) {
		this.content = content;
		this.updatedAt = Instant.now();
	}

	/**
	 * Determines whether this message is equal to another object based on the unique message ID.
	 *
	 * @param o the object to compare with this message
	 * @return true if the specified object is a Message with the same ID; false otherwise
	 */
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
