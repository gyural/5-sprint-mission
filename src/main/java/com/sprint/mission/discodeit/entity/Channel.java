package com.sprint.mission.discodeit.entity;

import java.io.Serializable;

public class Channel extends Common implements Serializable {
	private static final long serialVersionUID = 1L;

	private ChannelType channelType;
	private String name;
	private String description;

	public Channel(ChannelType channelType, String name, String description) {
		this.channelType = channelType;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" +
		  "Channel{" +
		  "channelType=" + channelType +
		  ", name='" + name + '\'' +
		  ", description='" + description + '\'' +
		  '}';
	}
}
