package com.sprint.mission.discodeit.domain.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelUser {
	private UUID channelId;
	private UUID userId;
	private boolean active;

	public ChannelUser(UUID channelId, UUID userId) {
		this.channelId = channelId;
		this.userId = userId;
		this.active = false; // 기본값으로 활성화 상태로 설정
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
