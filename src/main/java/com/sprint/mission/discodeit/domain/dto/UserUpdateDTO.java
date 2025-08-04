package com.sprint.mission.discodeit.domain.dto;

import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
	private UUID userId;
	private String newUsername;
	private String newEmail;
	private String newPassword;
	private BinaryContent newProfileImage;

}
