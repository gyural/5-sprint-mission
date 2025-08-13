package com.sprint.mission.discodeit.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateUserDTO {
	private final UUID userId;
	private final String newUsername;
	private final String newEmail;
	private final String newPassword;
	private final CreateBiContentDTO newProfilePicture;

}
