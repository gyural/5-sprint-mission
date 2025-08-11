package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.domain.request.UserLoginRequest;
import com.sprint.mission.discodeit.domain.response.UserLoginResponse;

public interface AuthService {

	public UserLoginResponse login(UserLoginRequest request);
}
