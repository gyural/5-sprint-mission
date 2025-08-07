package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.UserStatusCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserStatusService {
	private final UserStatusRepository userStatusRepository;
	private final UserRepository userRepository;

	public UserStatus create(UserStatusCreateDTO dto) {
		UUID userId = dto.getUserId();

		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		UserStatus userStatus = new UserStatus(userId);
		return userStatusRepository.save(userStatus);
	}

	public UserStatus find(UUID id) {
		return userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));
	}

	public List<UserStatus> findAll() {
		return userStatusRepository.findAll();
	}

	public void update(UserStatusUpdateDTO dto) {
		UUID id = dto.getId();

		UserStatus userStatus = userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	public void updateByUserId(UUID userID) {

		UserStatus userStatus = userStatusRepository.findByUserId(userID)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for User ID: " + userID));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	public void delete(UUID id) {
		if (userStatusRepository.isEmpty(id)) {
			throw new IllegalArgumentException("User status not found for ID: " + id);
		}
		userStatusRepository.delete(id);
	}

}
