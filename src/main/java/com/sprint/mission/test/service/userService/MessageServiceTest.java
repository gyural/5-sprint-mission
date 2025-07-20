package com.sprint.mission.test.service.userService;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.alarm.AlarmService;
import com.sprint.mission.discodeit.service.alarm.MessageAlarmService;
import com.sprint.mission.discodeit.service.jsf.ChanneluserService;
import com.sprint.mission.discodeit.service.jsf.JCFMessageService;
import com.sprint.mission.discodeit.service.jsf.JCFUserService;

public class MessageServiceTest {

	private final ChanneluserService channelUserService = new ChanneluserService();
	private final AlarmService alarmService = new AlarmService();
	private final MessageAlarmService messageAlarmService = new MessageAlarmService(channelUserService, alarmService);

	private final JCFUserService userService = new JCFUserService();

	private final JCFMessageService messageService = new JCFMessageService(userService, messageAlarmService);

	public void testCreateMessage() {
		System.out.print("Testing message creation...");

		// Given
		String content = "This is a test message";
		UUID channelId = UUID.randomUUID();
		UUID userID = UUID.randomUUID();
		String userName = "testUser1";

		User user = new User(userName);
		userService.data.put(userID, user);

		Message storedMessage = new Message(content, userID, channelId, userName);
		messageService.data.put(storedMessage.getId(), storedMessage);

		// When
		messageService.create(content, channelId, userID);

		// Then
		List<String> storedMessageContentList = messageService.data.values().stream()
		  .map(Message::getContent).toList();
		List<UUID> storedMessageAuthorIdList = messageService.data.values().stream()
		  .map(Message::getAuthorId).toList();

		boolean isMessageStored = storedMessageContentList.contains(content)
		  && storedMessageAuthorIdList.contains(userID);

		if (isMessageStored) {
			System.out.println("message created successfully✅");
		} else {
			throw new RuntimeException("failed to create message.");
		}

		// clear data after test
		messageService.data.clear();
	}

	public void testReadMessage() {
		System.out.print("Testing message reading...");

		// Given
		String content = "This is a test message";
		UUID channelId = UUID.randomUUID();
		UUID userID = UUID.randomUUID();
		String userName = "testUser1";

		User user = new User(userName);
		userService.data.put(userID, user);

		Message storedMessage = new Message(content, userID, channelId, userName);
		messageService.data.put(storedMessage.getId(), storedMessage);

		// When
		Message readMessage = messageService.read(storedMessage.getId());
		List<Message> readAllMessages = messageService.readAll();

		boolean isSuccess = readAllMessages.size() == 1
		  && readMessage.getContent().equals(content)
		  && readMessage.getAuthorId().equals(userID)
		  && readMessage.getChannelId().equals(channelId)
		  && readMessage.getAuthorName().equals(userName);

		// Then
		if (isSuccess) {
			System.out.println("message read successfully✅");
		} else {
			throw new RuntimeException("failed to read message.");
		}

		// clear data after test
		messageService.data.clear();

	}

	public void testUpdateMessage() {
		System.out.print("Testing message update...");

		// Given
		String content = "This is a test message";
		UUID channelId = UUID.randomUUID();
		UUID userID = UUID.randomUUID();
		String userName = "testUser1";

		User user = new User(userName);
		userService.data.put(userID, user);

		Message storedMessage = new Message(content, userID, channelId, userName);
		messageService.data.put(storedMessage.getId(), storedMessage);

		String newContent = "This is an updated test message";

		// When
		messageService.update(storedMessage.getId(), newContent);
		Message targetMessage = messageService.data.values().iterator().next();

		boolean isSuccess = targetMessage.getContent().equals(newContent);

		// Then
		if (isSuccess) {
			System.out.println("message updated successfully✅");
		} else {
			throw new RuntimeException("failed to update message.");
		}

		// clear data after test
		messageService.data.clear();

	}

	public void testDeleteMessage() {
		System.out.print("Testing message deletion...");

		// Given
		String content = "This is a test message";
		UUID channelId = UUID.randomUUID();
		UUID userID = UUID.randomUUID();
		String userName = "testUser1";

		User user = new User(userName);
		userService.data.put(userID, user);

		Message storedMessage = new Message(content, userID, channelId, userName);
		messageService.data.put(storedMessage.getId(), storedMessage);

		// When
		messageService.delete(storedMessage.getId());
		boolean isDeleted = messageService.data.isEmpty();

		// Then
		if (isDeleted) {
			System.out.println("message deleted successfully✅");
		} else {
			throw new RuntimeException("failed to delete message.");
		}

		// clear data after test
		messageService.data.clear();

	}

}
