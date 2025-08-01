package com.sprint.mission.enums;

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
