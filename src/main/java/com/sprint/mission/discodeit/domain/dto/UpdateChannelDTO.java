package com.sprint.mission.discodeit.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateChannelDTO {
	private final UUID id;
	private final String name;
	private final String description;

}
