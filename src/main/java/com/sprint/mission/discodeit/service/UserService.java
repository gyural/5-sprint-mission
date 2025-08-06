package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.User;

public interface UserService {
	/**
 * Creates a new user based on the provided user creation data.
 *
 * @param dto the data transfer object containing user creation details
 * @return the created User entity
 */
User create(UserCreateDTO dto);

	/**
 * Deletes the user identified by the specified UUID.
 *
 * @param userId the unique identifier of the user to delete
 */
void delete(UUID userId);

	/**
 * Updates an existing user with the information provided in the update DTO.
 *
 * @param dto the data transfer object containing updated user information
 */
void update(UserUpdateDTO dto);

	/**
 * Retrieves user information for the specified user ID as a read-only data transfer object.
 *
 * @param userId the unique identifier of the user to retrieve
 * @return a UserReadDTO containing the user's information
 */
UserReadDTO read(UUID userId);

	/**
 * Retrieves a list of all users.
 *
 * @return a list containing all User entities
 */
List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
