package com.sprint.mission.discodeit.domain.response;

import com.sprint.mission.discodeit.domain.entity.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateChannelResponse {

	private final Channel channel;
}
