package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Channel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Long createdAt;
	private Long updatedAt;
	private ChannelType channelType;
	private String name;
	private String description;

	public Channel(ChannelType channelType, String name, String description) {
		this.id = UUID.randomUUID();
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = null;
		this.channelType = channelType;
		this.name = name;
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
		this.updatedAt = System.currentTimeMillis();
	}

	public void setDescription(String description) {
		this.description = description;
		this.updatedAt = System.currentTimeMillis();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Channel channel = (Channel)o;
		return Objects.equals(id, channel.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

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
