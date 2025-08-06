package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class ChannelUser {
	private UUID channelId;
	private UUID userId;
	private boolean isActive;

	public ChannelUser(UUID channelId, UUID userId) {
		this.channelId = channelId;
		this.userId = userId;
		this.isActive = false; // 기본값으로 활성화 상태로 설정
	}

	public UUID getChannelId() {
		return channelId;
	}

	public void setChannelId(UUID channelId) {
		this.channelId = channelId;
	}

	public UUID getUserId() {
		return userId;
	}

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
