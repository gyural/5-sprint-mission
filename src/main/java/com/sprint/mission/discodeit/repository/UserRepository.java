package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.User;

@Repository
public interface UserRepository {

	/**
 * Persists the given user entity and returns the saved instance.
 *
 * @param user the user entity to be saved
 * @return the persisted user entity
 */
User save(User user);

	/**
 * Deletes the user with the specified UUID from the repository.
 *
 * @param userId the unique identifier of the user to delete
 */
void delete(UUID userId);

	Optional<User> find(UUID userId);

	List<User> findAll();

	boolean isEmpty(UUID userId);

	/**
 * Removes all user records from the repository.
 */
void deleteAll();

	/**
 * Returns the total number of users in the repository.
 *
 * @return the count of user entities
 */
Long count(); /**
 * Retrieves a user by their username.
 *
 * @param username the username to search for
 * @return an {@code Optional} containing the user if found, or empty if not found
 */

	Optional<User> findByUsername(String username);

	/**
 * Retrieves a user by their email address.
 *
 * @param username the email address to search for
 * @return an Optional containing the user if found, or empty if no user with the given email exists
 */
Optional<User> findByEmail(String username);

}
