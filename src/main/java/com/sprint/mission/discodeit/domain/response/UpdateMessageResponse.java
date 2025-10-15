package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateMessageResponse {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final String content;
	private final UUID authorId;
	private final UUID channelId;
	private final List<UUID> attachmentIds;
}
