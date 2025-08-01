package com.sprint.mission.discodeit.domain.entity;

public enum ChannelType {
	PUBLIC("PUBLIC"),
	PRIVATE("PRIVATE");

	private final String type;

	ChannelType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}
}
