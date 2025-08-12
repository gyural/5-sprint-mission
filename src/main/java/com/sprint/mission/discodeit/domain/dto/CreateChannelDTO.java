package com.sprint.mission.discodeit.domain.dto;

import java.util.List;

import com.sprint.mission.discodeit.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreateChannelDTO {

	private final String name;
	private final String description;
	private final List<User> members;

}
