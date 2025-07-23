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

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class FileChannelRepository implements ChannelRepository {

	private static final String DIR_NAME = "data";
	private static final String FILE_NAME = DIR_NAME + "/channel.ser";

	public FileChannelRepository() {
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
	public Channel create(ChannelType channelType, String name, String description) {

		Channel newChannel = new Channel(channelType, name, description);
		List<Channel> channels = findAll();
		channels = new java.util.ArrayList<>(channels);
		channels.add(newChannel);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(channels);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return newChannel;
	}

	@Override
	public Channel find(UUID id) {
		List<Channel> channels = findAll();
		return channels.stream()
		  .filter(channel -> channel.getId().equals(id))
		  .findFirst()
		  .orElseThrow(() -> new IllegalArgumentException("해당 id의 Channel이 없습니다: " + id));
	}

	@Override
	public List<Channel> findAll() {
		try (FileInputStream fis = new FileInputStream(FILE_NAME);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			if (obj instanceof List) {
				return (List<Channel>)obj;
			}
		} catch (Exception e) {
			// 파일이 없거나 읽기 실패 시 빈 리스트 반환
		}
		return List.of();
	}

	@Override
	public void delete(UUID id) {
		List<Channel> channels = findAll();
		int beforeSize = channels.size();
		channels.removeIf(channel -> channel.getId().equals(id));

		if (channels.size() == beforeSize) {
			throw new IllegalArgumentException("해당 id의 Channel이 없습니다: " + id);
		}

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(channels);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(UUID id, ChannelType channelType, String newChannelName, String newDescription) {
		List<Channel> channels = findAll();
		channels = new java.util.ArrayList<>(channels);
		Channel channelToUpdate = channels.stream()
		  .filter(channel -> channel.getId().equals(id))
		  .findFirst()
		  .orElseThrow(() -> new IllegalArgumentException("해당 id의 Channel이 없습니다: " + id));

		channelToUpdate.setUpdatedAt(System.currentTimeMillis());
		channelToUpdate.setName(newChannelName);
		channelToUpdate.setDescription(newDescription);

		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(channels);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isEmpty(UUID id) {
		List<Channel> channels = findAll();
		for (Channel channel : channels) {
			if (channel.getId().equals(id)) {
				return false; // 해당 ID의 채널이 존재함
			}
		}
		return true; // 해당 ID의 채널이 존재하지 않음
	}

	@Override
	public void deleteAll() {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(List.of()); // 빈 리스트로 덮어쓰기
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
