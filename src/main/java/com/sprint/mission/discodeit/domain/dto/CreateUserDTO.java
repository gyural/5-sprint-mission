package com.sprint.mission.discodeit.domain.dto;

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
	private final CreateBiContentDTO binaryContent;

}
