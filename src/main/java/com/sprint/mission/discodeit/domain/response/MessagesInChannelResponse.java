package com.sprint.mission.discodeit.domain.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MessagesInChannelResponse {
	private final List<MessageResponse> messages;
}
