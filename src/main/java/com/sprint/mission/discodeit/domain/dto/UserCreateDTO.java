package com.sprint.mission.discodeit.domain.dto;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserCreateDTO {
	private String username;
	private String email;
	private String password;
	private BinaryContent binaryContent;

	public User toUser() {
		return new User(username, email, password, null);
	}

}
