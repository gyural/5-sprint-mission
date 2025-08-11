package com.sprint.mission.discodeit.domain.dto;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateUserDTO {
	private final String username;
	private final String email;
	private final String password;
	private final BinaryContent binaryContent;

}
