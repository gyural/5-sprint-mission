package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.enums.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ReadChannelResponse {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final ChannelType channelType;
	private final String name;
	private final String description;
	private final Instant lastMessageAt;
	private final List<UUID> membersIDs;
}
