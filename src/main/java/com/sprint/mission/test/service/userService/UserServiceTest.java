package com.sprint.mission.test.service.userService;

import java.util.List;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jsf.JCFUserService;

public class UserServiceTest {

	private final JCFUserService userService = new JCFUserService();

	public void testCreateUser() {
		System.out.print("Testing user creation...");
		// Given
		String username1 = "testUser1";
		String username2 = "testUser2";

		// When
		userService.create(username1);
		userService.create(username2);

		// Then
		List<String> storedUserNameList
		  = userService.data.values().stream().map(User::getUsername).toList();

		boolean isUser1Stored = storedUserNameList.contains(username1);
		boolean isUser2Stored = storedUserNameList.contains(username2);

		if (isUser1Stored && isUser2Stored) {
			System.out.println("users created successfully✅");
		} else {
			throw new RuntimeException("failed to create users.");
		}

		// clear data after test
		userService.data.clear();
	}

	public void testReadUser() {
		System.out.print("Testing user reading...");

		// Given
		User User1 = new User("testUser1");
		User User2 = new User("testUser1");
		User User3 = new User("testUser1");

		userService.data.put(User1.getId(), User1);
		userService.data.put(User2.getId(), User2);
		userService.data.put(User3.getId(), User3);

		// When
		List<User> readAllResult = userService.readAll();

		User readByUser1Id = userService.read(User1.getId());
		User readByUser2Id = userService.read(User1.getId());
		User readByUser3Id = userService.read(User1.getId());

		// Then
		boolean isSuccess = readAllResult.size() == 3
		  && readByUser1Id.getUsername().equals(User1.getUsername())
		  && readByUser2Id.getUsername().equals(User2.getUsername())
		  && readByUser3Id.getUsername().equals(User3.getUsername());

		if (isSuccess) {
			System.out.println("users read successfully✅");
		} else {
			throw new RuntimeException("failed to read users.");
		}

		// clear data after test
		userService.data.clear();
	}

	public void testUpdateUser() {
		System.out.print("Testing user updating...");

		// Given
		User user1 = new User("testUser1");
		userService.data.put(user1.getId(), user1);
		String newUsername = "updatedUser1";
		// When
		userService.update(user1.getId(), newUsername);
		// Then
		User updatedUser = userService.data.get(user1.getId());

		if (updatedUser != null && updatedUser.getUsername().equals(newUsername)) {
			System.out.println("user updated successfully✅");
		} else {
			throw new RuntimeException("failed to update user.");
		}

		// clear data after test
		userService.data.clear();
	}

	public void testDeleteUser() {
		System.out.print("Testing user deletion...");

		// Given
		User user1 = new User("testUser1");
		userService.data.put(user1.getId(), user1);
		// When
		userService.delete(user1.getId());
		// Then
		boolean isDeleted = userService.data.get(user1.getId()) == null;
		if (isDeleted) {
			System.out.println("user deleted successfully✅");
		} else {
			throw new RuntimeException("failed to delete user.");
		}

		// clear data after test

	}

}
