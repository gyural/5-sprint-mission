package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.domain.dto.LoginParams;
import com.sprint.mission.discodeit.domain.entity.User;

public interface AuthService {

	public User login(LoginParams params);

}
