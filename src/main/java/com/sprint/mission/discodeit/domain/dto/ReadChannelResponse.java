package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.enums.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReadChannelResponse {
	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private ChannelType channelType;
	private String name;
	private String description;
	private Instant lastMessageAt;
	private List<UUID> membersIDList;

	/**
	 * Creates a {@code ReadChannelResponse} DTO from a {@code Channel} entity, the timestamp of the last message, and a list of member UUIDs.
	 *
	 * @param channel the channel entity to convert
	 * @param LastMessageAt the timestamp of the last message sent in the channel
	 * @param membersIDList the list of member UUIDs associated with the channel
	 * @return a {@code ReadChannelResponse} representing the channel's data
	 */
	public static ReadChannelResponse toReadChannelResponse(Channel channel, Instant LastMessageAt,
	  List<UUID> membersIDList) {
		return ReadChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .channelType(channel.getChannelType())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .lastMessageAt(LastMessageAt)
		  .membersIDList(membersIDList)
		  .build();
	}
}
