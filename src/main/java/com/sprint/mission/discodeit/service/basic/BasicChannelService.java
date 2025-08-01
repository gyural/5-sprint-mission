package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

	private final ChannelRepository channelRepository;
	private final MessageRepository messageRepository;

	@Override
	public Channel create(ChannelCreateDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();
		ChannelType channelType = dto.getChannelType();

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (channelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}
		return channelRepository.save(new Channel(channelType, name, description));
	}

	@Override
	public Channel read(UUID id) {
		return channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));
	}

	@Override
	public List<Channel> readAll() {
		return channelRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

	@Override
	public void update(ChannelUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("ChannelUpdateDTO cannot be null"));

		UUID id = dto.getId();
		String newChannelName = dto.getName();
		String newDescription = dto.getDescription();
		ChannelType newChannelType = dto.getChannelType();

		if (newChannelName == null || newChannelName.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (newDescription == null || newDescription.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (newChannelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}

		// 채널이 존재하는지 확인
		Channel targetChannel = channelRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Channel with ID " + id + " not found"));

		targetChannel.setChannelType(newChannelType);
		targetChannel.setName(newChannelName);
		targetChannel.setDescription(newDescription);

		channelRepository.save(targetChannel);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return channelRepository.isEmpty(id);
	}

	@Override
	public void deleteAll() {
		channelRepository.deleteAll();
		messageRepository.deleteAll();
	}
}
