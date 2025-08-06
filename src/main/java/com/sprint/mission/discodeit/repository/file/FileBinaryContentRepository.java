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
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "file"
)
public class FileBinaryContentRepository implements BinaryContentRepository {

	private final String FILE_NAME;

	/**
	 * Constructs a file-based repository for persisting BinaryContent entities.
	 *
	 * Initializes the storage file at the specified directory, creating parent directories if necessary.
	 * If the file does not exist, it is created and initialized with an empty list of BinaryContent.
	 *
	 * @param fileDirectory the directory path where the repository file will be stored
	 */
	public FileBinaryContentRepository(@Value("${discodeit.repository.file-directory}") String fileDirectory) {
		this.FILE_NAME = fileDirectory + "/binaryContent.ser";

		try {
			Path filePath = Paths.get(FILE_NAME);
			// 상위 디렉터리 생성
			Files.createDirectories(filePath.getParent());
			if (!Files.exists(filePath)) {
				try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
					 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(new ArrayList<BinaryContent>());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Saves a BinaryContent entity, replacing any existing entity with the same ID, and persists the updated list to the file.
	 *
	 * @param newBinaryContent the BinaryContent entity to save
	 * @return the saved BinaryContent entity
	 * @throws RuntimeException if an I/O error occurs during persistence
	 */
	@Override
	public BinaryContent save(BinaryContent newBinaryContent) {
		List<BinaryContent> binaryContents = findAll();

		binaryContents = new ArrayList<>(binaryContents);
		binaryContents.removeIf(exisitingEntity -> exisitingEntity.getId().equals(newBinaryContent.getId()));
		binaryContents.add(newBinaryContent);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(binaryContents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return newBinaryContent;
	}

	/**
	 * Adds the provided list of BinaryContent entities to the existing collection and persists the updated list to the file.
	 *
	 * @param newBinaryContents the list of BinaryContent entities to add
	 * @return the complete list of all persisted BinaryContent entities after the addition
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public List<BinaryContent> saveAll(List<BinaryContent> newBinaryContents) {
		List<BinaryContent> binaryContents = findAll();

		binaryContents = new ArrayList<>(binaryContents);
		binaryContents.addAll(newBinaryContents);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(binaryContents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return binaryContents;

	}

	/**
	 * Retrieves a binary content entity by its unique identifier.
	 *
	 * @param id the UUID of the binary content to find
	 * @return an {@code Optional} containing the matching {@code BinaryContent} if found, or empty if not present
	 */
	@Override
	public Optional<BinaryContent> find(UUID id) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getId().equals(id))
		  .findFirst();
	}

	/**
	 * Retrieves all stored {@link BinaryContent} entities from the file.
	 *
	 * @return a list of all {@link BinaryContent} entities, or an empty list if the file is missing or cannot be read
	 */
	@Override
	public List<BinaryContent> findAll() {
		try (FileInputStream fis = new FileInputStream(FILE_NAME);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			if (obj instanceof List) {
				return (List<BinaryContent>)obj;
			}
		} catch (Exception e) {
			// 파일이 없거나 읽기 실패 시 빈 리스트 반환
		}

		return List.of();
	}

	/**
	 * Retrieves all BinaryContent entities whose IDs are contained in the specified list.
	 *
	 * @param ids the list of UUIDs to match against stored entities
	 * @return a list of BinaryContent entities with IDs present in the provided list
	 */
	@Override
	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return findAll().stream()
		  .filter(binaryContent -> ids.contains(binaryContent.getId()))
		  .toList();
	}

	/**
	 * Removes the {@code BinaryContent} entity with the specified ID from persistent storage.
	 *
	 * @param id the UUID of the entity to delete
	 * @throws RuntimeException if an I/O error occurs during file operations
	 */
	@Override
	public void delete(UUID id) {
		List<BinaryContent> binaryContents = findAll();
		binaryContents.removeIf(binaryContent -> binaryContent.getId().equals(id));

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(binaryContents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks whether there is no BinaryContent entity with the specified ID.
	 *
	 * @param id the UUID of the BinaryContent to check for existence
	 * @return true if no entity with the given ID exists; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return findAll().stream()
		  .noneMatch(binaryContent -> binaryContent.getId().equals(id));
	}

	/**
	 * Removes all stored BinaryContent entities by overwriting the storage file with an empty list.
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
