package com.sprint.mission.discodeit.domain.response;

import com.sprint.mission.discodeit.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserLoginResponse {
	private boolean success;
	private User user;
}
