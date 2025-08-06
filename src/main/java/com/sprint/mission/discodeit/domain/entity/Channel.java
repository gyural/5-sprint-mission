package com.sprint.mission.discodeit.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.enums.ChannelType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Channel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;
	private ChannelType channelType;
	private String name;
	private String description;

	/**
	 * Constructs a new Channel with the specified type, name, and description.
	 *
	 * The channel is assigned a unique identifier and its creation timestamp is set to the current instant.
	 *
	 * @param channelType the type of the channel
	 * @param name the name of the channel
	 * @param description the description of the channel
	 */
	public Channel(ChannelType channelType, String name, String description) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.channelType = channelType;
		this.name = name;
		this.description = description;
	}

	/**
	 * Updates the channel's name and sets the updatedAt timestamp to the current instant.
	 *
	 * @param name the new name for the channel
	 */
	public void setName(String name) {
		this.name = name;
		this.updatedAt = Instant.now();
	}

	/**
	 * Updates the channel's description and sets the updatedAt timestamp to the current instant.
	 *
	 * @param description the new description for the channel
	 */
	public void setDescription(String description) {
		this.description = description;
		this.updatedAt = Instant.now();
	}

	/**
	 * Determines whether this Channel is equal to another object based on the unique identifier.
	 *
	 * @param o the object to compare with this Channel
	 * @return true if the specified object is a Channel with the same id; false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Channel channel = (Channel)o;
		return Objects.equals(id, channel.id);
	}

	/**
	 * Returns the hash code value for this Channel based on its unique identifier.
	 *
	 * @return the hash code of the Channel's id
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	/**
	 * Returns a string representation of the Channel, including all its fields.
	 *
	 * @return a string describing the Channel object
	 */
	@Override
	public String toString() {
		return "Channel{" +
		  "id=" + id +
		  ", createdAt=" + createdAt +
		  ", updatedAt=" + updatedAt +
		  ", channelType=" + channelType +
		  ", name='" + name + '\'' +
		  ", description='" + description + '\'' +
		  ", channelType=" + channelType +
		  '}';
	}
}
