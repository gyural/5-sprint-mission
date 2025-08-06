package com.sprint.mission.discodeit.repository.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "file"
)
public class FileUserStatusRepository implements UserStatusRepository {

	private final String FILE_NAME;

	/**
	 * Initializes the file-based user status repository, creating the storage file and its parent directories if they do not exist.
	 *
	 * @param fileDirectory the directory path where the user status data file will be stored
	 * @throws RuntimeException if an I/O error occurs during initialization
	 */
	public FileUserStatusRepository(@Value("${discodeit.repository.file-directory}") String fileDirectory) {
		this.FILE_NAME = fileDirectory + "/userStatus.ser";

		try {
			Path filePath = Paths.get(FILE_NAME);
			Files.createDirectories(filePath.getParent());
			if (!Files.exists(filePath)) {
				try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
					 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(new ArrayList<UserStatus>());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Saves or updates a UserStatus entity in the file-based repository.
	 *
	 * If a UserStatus with the same ID already exists, it is replaced. The updated list is persisted to the file.
	 *
	 * @param userStatus the UserStatus entity to save or update
	 * @return the saved UserStatus entity
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public UserStatus save(UserStatus userStatus) {
		List<UserStatus> userStatusList = new ArrayList<>(findAll());

		userStatusList.removeIf(u -> u.getId().equals(userStatus.getId())); // 중복된 ID 제거
		userStatusList.add(userStatus);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(userStatusList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return userStatus;
	}

	/**
	 * Retrieves a user status by its unique identifier.
	 *
	 * @param id the unique identifier of the user status
	 * @return an {@code Optional} containing the matching {@code UserStatus} if found, or empty if not found
	 */
	@Override
	public Optional<UserStatus> find(UUID id) {
		return findAll().stream().filter(u -> u.getId().equals(id)).findFirst();
	}

	/**
	 * Retrieves the first UserStatus associated with the specified user ID.
	 *
	 * @param userId the UUID of the user whose status is to be retrieved
	 * @return an Optional containing the UserStatus if found, or empty if not present
	 */
	@Override
	public Optional<UserStatus> findByUserId(UUID userId) {
		return findAll().stream()
		  .filter(u -> u.getUserId().equals(userId))
		  .findFirst();
	}

	/**
	 * Retrieves all stored {@link UserStatus} entities from the file.
	 *
	 * @return a list of all {@link UserStatus} objects; returns an empty list if the file does not exist or cannot be read.
	 */
	@Override
	public java.util.List<UserStatus> findAll() {
		try (FileInputStream fis = new FileInputStream(FILE_NAME);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			if (obj instanceof List) {
				return (List<UserStatus>)obj;
			}
		} catch (Exception e) {
			// 파일이 없거나 읽기 실패 시 빈 리스트 반환
		}

		return List.of();
	}

	/**
	 * Deletes the UserStatus with the specified ID from the file-based repository.
	 *
	 * @param id the unique identifier of the UserStatus to delete
	 * @throws IllegalArgumentException if no UserStatus with the given ID exists
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public void delete(UUID id) {
		List<UserStatus> userStatusList = new ArrayList<>(findAll());
		int beforeSize = userStatusList.size();
		userStatusList.removeIf(u -> u.getId().equals(id));

		if (userStatusList.size() == beforeSize) {
			throw new IllegalArgumentException("UserStatus with ID " + id + " not found");
		}

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(userStatusList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks whether there is no UserStatus entry with the specified ID.
	 *
	 * @param id the unique identifier to check for existence
	 * @return true if no UserStatus with the given ID exists; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return findAll().stream().noneMatch(u -> u.getId().equals(id));
	}

	/**
	 * Removes all stored UserStatus entries by overwriting the file with an empty list.
	 *
	 * @throws RuntimeException if an I/O error occurs during the file operation.
	 */
	@Override
	public void deleteAll() {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(new ArrayList<UserStatus>());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all UserStatus entries associated with the specified user ID from the file-based repository.
	 *
	 * @param userId the user ID whose associated UserStatus entries should be deleted
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public void deleteByUserId(UUID userId) {
		List<UserStatus> userStatusList = new ArrayList<>(findAll());
		userStatusList.removeIf(u -> u.getUserId().equals(userId));

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(userStatusList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
