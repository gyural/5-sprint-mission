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

	/**
	 * Creates a new UserStatus entity for the specified user.
	 *
	 * Validates that the provided user ID exists; otherwise, throws an IllegalArgumentException.
	 *
	 * @param dto the data transfer object containing the user ID for which to create the status
	 * @return the newly created UserStatus entity
	 * @throws IllegalArgumentException if the user ID is null or does not exist
	 */
	public UserStatus create(UserStatusCreateDTO dto) {
		UUID userId = dto.getUserId();

		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		UserStatus userStatus = new UserStatus(userId);
		return userStatusRepository.save(userStatus);
	}

	/**
	 * Retrieves a UserStatus entity by its unique identifier.
	 *
	 * @param id the unique identifier of the UserStatus to retrieve
	 * @return the UserStatus entity with the specified ID
	 * @throws IllegalArgumentException if no UserStatus is found for the given ID
	 */
	public UserStatus find(UUID id) {
		return userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));
	}

	/**
	 * Retrieves all UserStatus entities from the repository.
	 *
	 * @return a list of all UserStatus records
	 */
	public List<UserStatus> findAll() {
		return userStatusRepository.findAll();
	}

	/**
	 * Updates the timestamp of an existing UserStatus entity identified by the ID in the provided DTO.
	 *
	 * @param dto Data transfer object containing the ID of the UserStatus to update.
	 * @throws IllegalArgumentException if no UserStatus exists for the given ID.
	 */
	public void update(UserStatusUpdateDTO dto) {
		UUID id = dto.getId();

		UserStatus userStatus = userStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for ID: " + id));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	/**
	 * Updates the timestamp of the UserStatus entity associated with the specified user ID.
	 *
	 * @param userID the unique identifier of the user whose status should be updated
	 * @throws IllegalArgumentException if no UserStatus is found for the given user ID
	 */
	public void updateByUserId(UUID userID) {

		UserStatus userStatus = userStatusRepository.findByUserId(userID)
		  .orElseThrow(() -> new IllegalArgumentException("User status not found for User ID: " + userID));

		userStatus.setUpdatedAt();

		userStatusRepository.save(userStatus);

	}

	/**
	 * Deletes the user status entity with the specified ID.
	 *
	 * @param id the unique identifier of the user status to delete
	 * @throws IllegalArgumentException if no user status exists for the given ID
	 */
	public void delete(UUID id) {
		if (userStatusRepository.isEmpty(id)) {
			throw new IllegalArgumentException("User status not found for ID: " + id);
		}
		userStatusRepository.delete(id);
	}

}
