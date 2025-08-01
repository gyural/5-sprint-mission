package com.sprint.mission.discodeit.domain.dto;

import com.sprint.mission.discodeit.domain.entity.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelCreateDTO {

	private ChannelType channelType;
	private String name;
	private String description;

}
