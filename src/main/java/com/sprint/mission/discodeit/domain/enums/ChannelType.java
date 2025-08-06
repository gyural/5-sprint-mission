package com.sprint.mission.discodeit.domain.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
	PUBLIC("PUBLIC"),
	PRIVATE("PRIVATE");

	private final String type;

	/**
	 * Constructs a ChannelType enum constant with the specified string value.
	 *
	 * @param type the string representation of the channel type
	 */
	ChannelType(String type) {
		this.type = type;
	}

	/**
	 * Returns the string representation of the channel type.
	 *
	 * @return the string value associated with this channel type
	 */
	@Override
	public String toString() {
		return type;
	}
}
