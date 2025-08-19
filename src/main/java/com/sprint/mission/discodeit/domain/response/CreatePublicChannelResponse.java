package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.Channel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePublicChannelResponse {
	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private String type;
	private String name;
	private String description;

	public static CreatePublicChannelResponse toCreatePublicChannelResponse(Channel channel) {
		return CreatePublicChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .type(channel.getChannelType().name())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .build();
	}
}
