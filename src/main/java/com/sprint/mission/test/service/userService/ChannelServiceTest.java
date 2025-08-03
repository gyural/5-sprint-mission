package com.sprint.mission.test.service.userService;

import java.util.List;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.jsf.JCFChannelService;
import com.sprint.mission.discodeit.service.jsf.JCFMessageService;
import com.sprint.mission.discodeit.service.jsf.JCFUserService;

public class ChannelServiceTest {

	private final JCFUserService userService = new JCFUserService();
	private final JCFMessageService messageService = new JCFMessageService(userService);
	private final JCFChannelService channelService = new JCFChannelService(userService, messageService);

	public void testCreateChannel() {
		System.out.print("Testing channel creation...");
		// Given
		String channel1Name = "testChannel1";
		String channel1Description = "This is a test channel 1";

		String channe2Name = "testChannel2";
		String channel2Description = "This is a test channel 2";

		// When
		channelService.create(channel1Name, channel1Description);
		channelService.create(channe2Name, channel2Description);

		// Then
		List<String> storedChannelNameList
		  = channelService.data.values().stream().map(Channel::getName).toList();
		boolean isChannel1Stored = storedChannelNameList.contains(channel1Name);
		boolean isChannel2Stored = storedChannelNameList.contains(channe2Name);

		List<String> storedChannelDescriptionList
		  = channelService.data.values().stream().map(Channel::getDescription).toList();
		boolean isChannel1DescriptionStored = storedChannelDescriptionList.contains(channel1Description);
		boolean isChannel2DescriptionStored = storedChannelDescriptionList.contains(channel2Description);

		boolean isSuccess = isChannel1Stored && isChannel2Stored
		  && isChannel1DescriptionStored && isChannel2DescriptionStored;

		if (isSuccess) {
			System.out.println("channels created successfully✅");
		} else {
			throw new IllegalArgumentException("failed to create channels.");
		}

		// clear data after test
		channelService.data.clear();
	}

	public void testReadChannel() {
		System.out.print("Testing channel reading...");

		// Given
		Channel channel1 = new Channel("testChannel1", "This is a test channel 1");
		Channel channel2 = new Channel("testChannel2", "This is a test channel 2");
		Channel channel3 = new Channel("testChannel3", "This is a test channel 3");

		channelService.data.put(channel1.getId(), channel1);
		channelService.data.put(channel2.getId(), channel2);
		channelService.data.put(channel3.getId(), channel3);

		// When
		List<Channel> readAllResult = channelService.readAll();
		Channel readByChannel1Id = channelService.read(channel1.getId());
		Channel readByChannel2Id = channelService.read(channel2.getId());
		Channel readByChannel3Id = channelService.read(channel3.getId());

		// Then
		boolean isSuccess = readAllResult.size() == 3
		  && readByChannel1Id.getName().equals(channel1.getName())
		  && readByChannel2Id.getName().equals(channel2.getName())
		  && readByChannel3Id.getName().equals(channel3.getName());

		if (isSuccess) {
			System.out.println("channels read successfully✅");
		} else {
			throw new IllegalArgumentException("failed to read channels.");
		}

		// clear data after test
		channelService.data.clear();
	}

	public void testUpdateChannel() {
		System.out.print("Testing channel updating...");

		// Given
		Channel channel1 = new Channel("testChannel1", "This is a test channel 1");
		channelService.data.put(channel1.getId(), channel1);

		String newChannelName = "updatedTestChannel1";
		String newChannelDescription = "This is an updated test channel 1";

		// When
		channelService.update(channel1.getId(), newChannelName, newChannelDescription);

		// Then
		Channel targetChannel = channelService.data.get(channel1.getId());
		boolean isSuccess = targetChannel.getName().equals(newChannelName)
		  && targetChannel.getDescription().equals(newChannelDescription);

		if (isSuccess) {
			System.out.println("channel updated successfully✅");
		} else {
			throw new IllegalArgumentException("failed to update channel.");
		}

		// clear data after test
		channelService.data.clear();
	}

	public void testDeleteChannel() {
		System.out.print("Testing channel deletion...");

		// Given
		Channel channel1 = new Channel("testChannel1", "This is a test channel 1");
		channelService.data.put(channel1.getId(), channel1);

		// When
		channelService.delete(channel1.getId());

		// Then
		boolean isSuccess = channelService.data.isEmpty();

		if (isSuccess) {
			System.out.println("channel deleted successfully✅");
		} else {
			throw new IllegalArgumentException("failed to delete channel.");
		}

		// clear data after test
		channelService.data.clear();
	}
}
