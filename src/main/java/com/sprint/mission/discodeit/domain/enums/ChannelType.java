package com.sprint.mission.discodeit.domain.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
	PUBLIC("PUBLIC"),
	PRIVATE("PRIVATE");

	private final String type;

	ChannelType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
