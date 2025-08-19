package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateChannelResponse {

	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private String type;
	private String name;
	private String description;

	public static UpdateChannelResponse toUpdateChannelResponse(Channel channel) {
		return UpdateChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .type(channel.getChannelType().toString())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .build();
	}
}
