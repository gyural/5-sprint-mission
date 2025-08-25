package com.sprint.mission.discodeit.domain.request;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class UserCreateRequest {
	@NotNull
	@NotBlank
	private final String username;
	@NotNull
	@NotBlank
	private final String email;
	@NotNull
	@NotBlank
	private final String password;
	@Valid
	private final CreateBiContentDTO profilePicture;
}
