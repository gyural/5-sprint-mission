package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreateUserStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
	private final UserStatusRepository userStatusRepository;
	private final UserRepository userRepository;

	@Override
	public UserStatus create(CreateUserStatusDTO dto) {
		UUID userId = dto.getUserId();

		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		UserStatus userStatus = new UserStatus(userId);
		return userStatusRepository.save(userStatus);
	}

	@Override
	public UserStatus find(UUID id) {
		return userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));
	}

	@Override
	public List<UserStatus> findAll() {
		return userStatusRepository.findAll();
	}

	@Override
	public void update(UpdateUserStatusDTO dto) {
		UUID id = dto.getId();

		UserStatus userStatus = userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	@Override
	public void updateByUserId(UUID userID) {

		UserStatus userStatus = userStatusRepository.findByUserId(userID)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for User ID: " + userID));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	@Override
	public void delete(UUID id) {
		if (userStatusRepository.isEmpty(id)) {
			throw new IllegalArgumentException("User status not found for ID: " + id);
		}
		userStatusRepository.delete(id);
	}

}
