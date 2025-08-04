package com.sprint.mission.discodeit.service.basic;

import static com.sprint.mission.discodeit.domain.dto.ReadChannelResponse.*;
import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.enums.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

	private final ChannelRepository channelRepository;
	private final MessageRepository messageRepository;
	private final UserStatusRepository userStatusRepository;

	@Override
	public Channel createPublic(ChannelCreateDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}

		return channelRepository.save(new Channel(PUBLIC, name, description));
	}

	@Override
	public Channel createPrivate(ChannelCreateDTO dto) {

		Channel newChannel = new Channel(PRIVATE, null, null);

		dto.getMembers().stream().forEach(
		  member -> userStatusRepository.save(
			new UserStatus(member.getId(), newChannel.getId()))
		);

		return channelRepository.save(newChannel);
	}

	@Override
	public ReadChannelResponse read(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		// 1. 가장 최근 메시지 가 언제인지 확인
		List<Message> messages = messageRepository.findAllByChannelId(id);
		Instant lastMessageAt = messages.isEmpty() ? null
		  : getLastEditAt(messages);

		List<UUID> membersIDList = channel.getChannelType() == PRIVATE ?
		  userStatusRepository.findByChannelId(id)
			.stream()
			.map(UserStatus::getUserId)
			.toList()
		  : new ArrayList<>();

		return toReadChannelResponse(channel, lastMessageAt, membersIDList);
	}

	@Override
	public List<ReadChannelResponse> findAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  // 필터링: PUBLIC 채널 또는 사용자가 참여한 PRIVATE 채널
		  .filter(c -> c.getChannelType() == PUBLIC ||
			userStatusRepository.findByUserId(userId)
			  .stream()
			  .anyMatch(us -> us.getChannelId().equals(c.getId())))
		  // 각 채널에 대해 메시지와 멤버 정보를 포함한 ReadChannelResponse 생성
		  .map(c -> {
				messageRepository.findAllByChannelId(c.getId());
				List<Message> messages = messageRepository.findAllByChannelId(c.getId());
				Instant lastMessageAt = messages.isEmpty() ? null
				  : getLastEditAt(messages);

				List<UUID> membersIDList = c.getChannelType() == PRIVATE ?
				  userStatusRepository.findByChannelId(c.getId())
					.stream()
					.map(UserStatus::getUserId)
					.toList()
				  : new ArrayList<>();

				return toReadChannelResponse(c, lastMessageAt, membersIDList);
			}
		  )
		  .toList();

	}

	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);
		// 연관된 유저 상태도 삭제
		userStatusRepository.deleteByChannelId(id);

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

		if (targetChannel.getChannelType() == PRIVATE) {
			throw new RuntimeException(
			  "Cannot update a private channel's type or members directly. Please use the appropriate service method.");
		}

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

	private Instant getLastEditAt(List<Message> messages) {
		return messages.stream().map(this::getMessageLastEditAt)
		  .max(Instant::compareTo)
		  .orElseThrow(() -> new NoSuchElementException("No messages found"));
	}

	private Instant getMessageLastEditAt(Message message) {
		return message.getUpdatedAt() != null ? message.getUpdatedAt() : message.getCreatedAt();
	}

}
