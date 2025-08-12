package com.sprint.mission.discodeit.domain.response;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreatePrivateChannelResponse {

	private final Channel channel;
	private final List<UUID> userIds;
}
