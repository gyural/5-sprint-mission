package com.sprint.mission.discodeit.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginRequest {
	@NotNull
	@NotBlank
	private String username;
	@NotNull
	@NotBlank
	private String password;
}
