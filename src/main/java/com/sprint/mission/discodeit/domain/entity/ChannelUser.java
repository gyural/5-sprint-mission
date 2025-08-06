package com.sprint.mission.discodeit.domain.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelUser {
	private UUID channelId;
	private UUID userId;
	private boolean isActive;

	/**
	 * Constructs a ChannelUser with the specified channel and user IDs, setting the active status to false by default.
	 *
	 * @param channelId the unique identifier of the channel
	 * @param userId the unique identifier of the user
	 */
	public ChannelUser(UUID channelId, UUID userId) {
		this.channelId = channelId;
		this.userId = userId;
		this.isActive = false; // 기본값으로 활성화 상태로 설정
	}

	/**
	 * Returns whether the user is currently active in the channel.
	 *
	 * @return {@code true} if the user is active in the channel; {@code false} otherwise
	 */
	public boolean isActive() {
		return isActive;
	}

	@Override
	public String toString() {
		return
		  "ChannelUser{" +
			"channelId=" + channelId +
			", userId=" + userId +
			'}';
	}
}
