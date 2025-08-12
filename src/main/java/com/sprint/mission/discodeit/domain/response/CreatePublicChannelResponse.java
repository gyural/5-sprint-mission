package com.sprint.mission.discodeit.domain.response;

import com.sprint.mission.discodeit.domain.entity.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreatePublicChannelResponse {
	private Channel channel;
}
