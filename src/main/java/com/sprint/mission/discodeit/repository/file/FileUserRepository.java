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
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class FileUserRepository implements UserRepository {

	private static final String DIR_NAME = "data";
	private static final String FILE_NAME = DIR_NAME + "/user.ser";

	public FileUserRepository() {
		try {
			Path dirPath = Paths.get(DIR_NAME);
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
			}
			Path filePath = Paths.get(FILE_NAME);
			if (!Files.exists(filePath)) {
				try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
					 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(new ArrayList<Message>());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User create(String username, String email, String password) {
		User newUser = new User(username, email, password);
		List<User> users = findAll();

		if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
			throw new IllegalArgumentException("Username already exists");
		}

		users = new ArrayList<>(users);
		users.add(newUser);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(users);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return newUser;
	}

	@Override
	public void delete(UUID userId) {
		List<User> users = findAll();
		int beforeSize = users.size();
		users.removeIf(user -> user.getId().equals(userId));

		if (users.size() == beforeSize) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(users);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(UUID userId, String newUsername, String newEmail, String newPassword) {
		List<User> users = findAll();
		User userToUpdate = users.stream()
		  .filter(user -> user.getId().equals(userId))
		  .findFirst()
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

		userToUpdate.setUsername(newUsername);
		userToUpdate.setEmail(newEmail);
		userToUpdate.setPassword(newPassword);
		userToUpdate.setUpdatedAt(System.currentTimeMillis());

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(users);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User find(UUID userId) {
		return findAll().stream()
		  .filter(user -> user.getId().equals(userId))
		  .findFirst()
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
	}

	@Override
	public List<User> findAll() {
		try (FileInputStream fis = new FileInputStream(FILE_NAME);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			if (obj instanceof List) {
				return (List<User>)obj;
			}
		} catch (Exception e) {
			// 파일이 없거나 읽기 실패 시 빈 리스트 반환
		}

		return List.of();
	}

	@Override
	public boolean isEmpty(UUID userId) {
		return findAll().stream()
		  .noneMatch(user -> user.getId().equals(userId));
	}

	@Override
	public void deleteAll() {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(new ArrayList<User>());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
