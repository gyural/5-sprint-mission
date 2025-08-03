package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class JCFMessageService implements MessageService {

	private final JCFMessageRepository messageRepository;
	private final JCFChannelRepository channelRepository;

	private final UserService userService;
	// private final MessageAlarmService messageAlarmService;

	public JCFMessageService(JCFMessageRepository messageRepository, JCFChannelRepository channelRepository,
	  UserService userService) {
		this.messageRepository = messageRepository;
		this.channelRepository = channelRepository;
		this.userService = userService;
	}

	@Override
	public Message create(String content, UUID channelId, UUID userId) {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}

		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}

		// 1. 데이터 저장
		// 2. 채널에 참여한 사용자들에게 알림을 전송
		// messageAlarmService.sendMessageAlarm(newMessage);

		return messageRepository.create(content, channelId, userId);
	}

	@Override
	public Message read(UUID id) {
		return messageRepository.find(id);
	}

	@Override
	public List<Message> readAll() {
		return messageRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		messageRepository.delete(id);
	}

	@Override
	public void deleteAll() {
		messageRepository.deleteAll();

	}

	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		// 채널에 속한 모든 메시지를 삭제
		messageRepository.deleteByChannelId(channelId);
	}

	@Override
	public void update(UUID id, String newContent) {

		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}

		if (id == null || messageRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Message ID cannot be null or empty");
		}

		// 메시지 내용 수정
		messageRepository.update(id, newContent);
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return messageRepository.findAllByChannelId(channelId);
	}

	@Override
	public boolean isEmpty(UUID channelId) {
		return messageRepository.isEmpty(channelId);
	}
}
