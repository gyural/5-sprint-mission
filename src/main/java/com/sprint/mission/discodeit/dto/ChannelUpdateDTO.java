package com.sprint.mission.discodeit.dto;

import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelUpdateDTO {
	private UUID id;
	private ChannelType channelType;
	private String name;
	private String description;

}
