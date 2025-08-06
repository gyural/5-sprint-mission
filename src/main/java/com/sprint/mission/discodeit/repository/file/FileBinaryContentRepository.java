package com.sprint.mission.discodeit.repository.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

@Repository
public class FileBinaryContentRepository implements BinaryContentRepository {

	private static final String DIR_NAME = "data";
	private static final String FILE_NAME = DIR_NAME + "/binaryContent.ser";

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

	@Override
	public Optional<BinaryContent> find(UUID id) {
		return findAll().stream()
		  .filter(binaryContent -> binaryContent.getId().equals(id))
		  .findFirst();
	}

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

	@Override
	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return findAll().stream()
		  .filter(binaryContent -> ids.contains(binaryContent.getId()))
		  .toList();
	}

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

	@Override
	public boolean isEmpty(UUID id) {
		return findAll().stream()
		  .noneMatch(binaryContent -> binaryContent.getId().equals(id));
	}

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
