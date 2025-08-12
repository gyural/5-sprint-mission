package com.sprint.mission.discodeit.service.basic;

import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.domain.enums.ChannelType;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

	private final ChannelRepository channelRepository;
	private final MessageRepository messageRepository;
	private final ReadStatusRepository readStatusRepository;

	@Override
	public Channel createPublic(CreatePublicChannelDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();

		return channelRepository.save(new Channel(PUBLIC, name, description));
	}

	@Override
	public Channel createPrivate(CreatePrivateChannelDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();

		Channel newChannel = new Channel(PRIVATE, name, description);

		dto.getUserIds().forEach(
		  id -> readStatusRepository.save(
			new ReadStatus(id, newChannel.getId()))
		);

		return channelRepository.save(newChannel);
	}

	@Override
	public ReadChannelResponse readPrivate(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		// 1. 가장 최근 메시지 가 언제인지 확인
		// TODO 추후 개선 필요 파일저장이라 SQL 불가능... (풀스캔 위험)
		List<Message> messages = messageRepository.findAllByChannelId(id);
		Instant lastMessageAt = messages.isEmpty() ? null
		  : getLastEditAt(messages);

		List<UUID> membersIDList = readStatusRepository.findAllByChannelId(id)
		  .stream()
		  .map(ReadStatus::getUserId)
		  .toList();

		return toReadChannelResponse(channel, lastMessageAt, membersIDList);
	}

	@Override
	public ReadChannelResponse readPublic(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		// 1. 가장 최근 메시지 가 언제인지 확인
		// TODO 추후 개선 필요 파일저장이라 SQL 불가능... (풀스캔 위험)
		List<Message> messages = messageRepository.findAllByChannelId(id);
		Instant lastMessageAt = messages.isEmpty() ? null
		  : getLastEditAt(messages);

		return toReadChannelResponse(channel, lastMessageAt, List.of());
	}

	@Override
	public List<ReadChannelResponse> readAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  // 필터링: PUBLIC 채널 또는 사용자가 참여한 PRIVATE 채널
		  .filter(c -> c.getChannelType() == PUBLIC ||
			readStatusRepository.findAllByUserId(userId)
			  .stream()
			  .anyMatch(us -> us.getChannelId().equals(c.getId())))
		  // 각 채널에 대해 메시지와 멤버 정보를 포함한 ReadChannelResponse 생성
		  .map(c -> {
				messageRepository.findAllByChannelId(c.getId());
				List<Message> messages = messageRepository.findAllByChannelId(c.getId());
				Instant lastMessageAt = messages.isEmpty() ? null
				  : getLastEditAt(messages);

				List<UUID> membersIDList = c.getChannelType() == PRIVATE ?
				  readStatusRepository.findAllByChannelId(c.getId())
					.stream()
					.map(ReadStatus::getUserId)
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
		readStatusRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

	@Override
	public Channel update(UpdateChannelDTO dto) {
		UUID id = dto.getId();
		String newChannelName = dto.getName();
		String newDescription = dto.getDescription();
		ChannelType newChannelType = dto.getChannelType();

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

		return channelRepository.save(targetChannel);
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

	private ReadChannelResponse toReadChannelResponse(Channel channel, Instant LastMessageAt,
	  List<UUID> membersIDList) {

		return ReadChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .channelType(channel.getChannelType())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .lastMessageAt(LastMessageAt)
		  .membersIDs(membersIDList)
		  .build();
	}

}
