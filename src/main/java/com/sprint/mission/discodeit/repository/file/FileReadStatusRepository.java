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

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "file"
)
public class FileReadStatusRepository implements ReadStatusRepository {

	private final String FILE_NAME;

	/**
	 * Constructs a file-based repository for storing and retrieving {@code ReadStatus} entities.
	 *
	 * Initializes the storage file and its parent directories if they do not exist, creating an empty list of {@code ReadStatus} as the initial file content.
	 *
	 * @param fileDirectory the directory path where the serialized data file will be stored
	 * @throws RuntimeException if an I/O error occurs during initialization
	 */
	public FileReadStatusRepository(@Value("${discodeit.repository.file-directory}") String fileDirectory) {
		this.FILE_NAME = fileDirectory + "/readStatus.ser";

		try {
			Path filePath = Paths.get(FILE_NAME);
			Files.createDirectories(filePath.getParent());
			if (!Files.exists(filePath)) {
				try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
					 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(new ArrayList<ReadStatus>());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Saves the given ReadStatus to the file, replacing any existing entry with the same ID.
	 *
	 * @param readStatus the ReadStatus entity to save
	 * @return the saved ReadStatus
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public ReadStatus save(ReadStatus readStatus) {
		List<ReadStatus> binaryContents = findAll();

		binaryContents = new ArrayList<>(binaryContents);
		binaryContents.removeIf(existingStatus -> existingStatus.getId().equals(readStatus.getId()));
		binaryContents.add(readStatus);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(binaryContents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return readStatus;
	}

	/**
	 * Retrieves a {@link ReadStatus} by its unique identifier.
	 *
	 * @param id the unique identifier of the {@link ReadStatus} to find
	 * @return an {@link Optional} containing the matching {@link ReadStatus} if found, or empty if not present
	 */
	@Override
	public Optional<ReadStatus> find(UUID id) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getId().equals(id))
		  .findFirst();
	}

	/**
	 * Retrieves all ReadStatus entries associated with the specified user ID.
	 *
	 * @param userId the UUID of the user whose ReadStatus entries are to be retrieved
	 * @return a list of ReadStatus objects for the given user ID
	 */
	@Override
	public List<ReadStatus> findAllByUserId(UUID userId) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getUserId().equals(userId)).toList();
	}

	/**
	 * Retrieves all ReadStatus entries associated with the specified channel ID.
	 *
	 * @param channelId the ID of the channel to filter by
	 * @return a list of ReadStatus objects for the given channel ID
	 */
	@Override
	public List<ReadStatus> findAllByChannelId(UUID channelId) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getChannelId().equals(channelId)).toList();
	}

	/**
	 * Retrieves the first {@code ReadStatus} matching both the specified user ID and channel ID.
	 *
	 * @param userId the user ID to match
	 * @param channelId the channel ID to match
	 * @return an {@code Optional} containing the matching {@code ReadStatus}, or empty if none found
	 */
	@Override
	public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getUserId().equals(userId))
		  .filter(binaryContent -> binaryContent.getChannelId().equals(channelId))
		  .findFirst();
	}

	/**
	 * Retrieves all stored {@link ReadStatus} entities from the file.
	 *
	 * @return a list of all {@link ReadStatus} objects; returns an empty list if the file does not exist or cannot be read.
	 */
	@Override
	public List<ReadStatus> findAll() {
		try (FileInputStream fis = new FileInputStream(FILE_NAME);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			if (obj instanceof List) {
				return (List<ReadStatus>)obj;
			}
		} catch (Exception e) {
			// 파일이 없거나 읽기 실패 시 빈 리스트 반환
		}

		return List.of();
	}

	/**
	 * Removes the {@code ReadStatus} entry with the specified ID from persistent storage.
	 *
	 * @param id the unique identifier of the {@code ReadStatus} to remove
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public void delete(UUID id) {
		List<ReadStatus> binaryContents = findAll();
		binaryContents.removeIf(binaryContent -> binaryContent.getId().equals(id));

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(binaryContents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Checks whether a ReadStatus with the specified ID exists in the repository.
	 *
	 * @param id the unique identifier of the ReadStatus to check
	 * @return true if no ReadStatus with the given ID exists; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return findAll().stream()
		  .noneMatch(binaryContent -> binaryContent.getId().equals(id));
	}

	/**
	 * Deletes all ReadStatus entries associated with the specified channel ID from the file-based repository.
	 *
	 * @param channelId the ID of the channel whose ReadStatus entries should be removed
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public void deleteByChannelId(UUID channelId) {
		List<ReadStatus> readStatuses = findAll();
		readStatuses.removeIf(readStatus -> readStatus.getChannelId().equals(channelId));

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(readStatuses);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all stored read status data by overwriting the file with an empty list.
	 *
	 * @throws RuntimeException if an I/O error occurs during the file operation.
	 */
	@Override
	public void deleteAll() {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(new ArrayList<BinaryContent>());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
