package com.sprint.mission.discodeit.domain.request;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateUserRequest {

	private final String username;
	private final String email;
	private final String password;
	@Valid
	private final CreateBiContentDTO profilePicture;
}
